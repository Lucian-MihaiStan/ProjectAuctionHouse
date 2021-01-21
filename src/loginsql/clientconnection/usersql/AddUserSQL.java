package loginsql.clientconnection.usersql;

import client.Client;
import client.IndividualPerson;
import client.LegalPerson;
import loginsql.MySQLConnection;
import socketserver.Main;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class AddUserSQL {
    private static final MySQLConnection mySQLConnection = Main.mySQLConnection;

    public void addClientSQL(Client client) {

        String query = "INSERT INTO client (first_name, last_name, address, noParticipation, noAuctionsWon)" +
                " values (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setInt(4, client.getNoParticipation());
            preparedStatement.setInt(5, client.getWonAuctions());

            preparedStatement.execute();

            if (client instanceof IndividualPerson) addUserIndividualPerson((IndividualPerson) client);
            else addUserLegalPerson((LegalPerson) client);
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

    private void addUserIndividualPerson(IndividualPerson individualPerson) {
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
