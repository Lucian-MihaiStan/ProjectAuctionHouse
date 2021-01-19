package loginsql.clientconnection.usersql;

import client.Client;
import client.IndividualPerson;
import client.LegalPerson;
import loginsql.clientconnection.MySQLConnectionClient;
import main.Main;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class AddUserSQL {
    MySQLConnectionClient mySQLConnectionClient = Main.mySQLConnectionClient;

    public void addUserClient(Client client) {
        String query = "INSERT INTO client (idClient, first_name, last_name, address, noParticipation, noAuctionsWon)" +
                " values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = mySQLConnectionClient.getConnection().prepareStatement(query)){
            preparedStatement.setInt(1, client.getId());
            preparedStatement.setString(2, client.getFirstName());
            preparedStatement.setString(3, client.getLastName());
            preparedStatement.setString(4, client.getAddress());
            preparedStatement.setInt(5, client.getNoParticipation());
            preparedStatement.setInt(6, client.getWonAuctions());

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

        try(PreparedStatement preparedStatement = mySQLConnectionClient.getConnection().prepareStatement(query)) {
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

        try(PreparedStatement preparedStatement = mySQLConnectionClient.getConnection().prepareStatement(query)){
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
