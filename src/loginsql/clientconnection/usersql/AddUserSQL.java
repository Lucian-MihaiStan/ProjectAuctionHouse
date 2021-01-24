package loginsql.clientconnection.usersql;

import client.User;
import client.legalperson.LegalPerson;
import loginsql.MySQLConnection;
import socketserver.ServerClientThread;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class AddUserSQL {
    private static final MySQLConnection mySQLConnection = ServerClientThread.mySQLConnection;

    public void addClientSQL(User user) {

        String query = "INSERT INTO client (username, first_name, last_name, address, noParticipation, noAuctionsWon)" +
                " values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setInt(5, user.getNoParticipation());
            preparedStatement.setInt(6, user.getWonAuctions());

            preparedStatement.execute();

            if (user instanceof client.individualperson.IndividualPerson) addUserIndividualPerson((client.individualperson.IndividualPerson) user);
            else addUserLegalPerson((LegalPerson) user);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private void addUserLegalPerson(LegalPerson legalPerson) {
        String query = "INSERT INTO legalperson (id_legalPerson, socialCapital, company)" +
                " VALUES ((SELECT id FROM client WHERE id = (SELECT MAX(id) FROM client)), ?, ?)";

        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setDouble(1, legalPerson.getSocialCapital());
            preparedStatement.setString(2, String.valueOf(legalPerson.getTypeCompany()));
            preparedStatement.execute();
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private void addUserIndividualPerson(client.individualperson.IndividualPerson individualPerson) {
        String query = "INSERT INTO individualperson (id_individualPerson, birthDate)" +
                " VALUES ((SELECT id FROM client WHERE id = (SELECT MAX(id) FROM client)), ?)";

        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            java.util.Date individualDate = individualPerson.getBirthDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(individualDate);

            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.execute();

        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }
}
