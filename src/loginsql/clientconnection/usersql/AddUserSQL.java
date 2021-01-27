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
    private final MySQLConnection mySQLConnection;

    public static String password;

    public AddUserSQL(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }

    public void addClientSQL(User user) {
        try {
            mySQLConnection.realizeConnection("root", "lucian2000");
        } catch (SQLException | ClassNotFoundException errorSQL) {
            errorSQL.printStackTrace();
        }
        String query = "INSERT INTO client (username, email, first_name, last_name, address, noParticipation, noAuctionsWon)" +
                " values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setInt(6, user.getNoParticipation());
            preparedStatement.setInt(7, user.getWonAuctions());

            preparedStatement.execute();

            if (user instanceof client.individualperson.IndividualPerson) addUserIndividualPerson((client.individualperson.IndividualPerson) user);
            else addUserLegalPerson((LegalPerson) user);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        createUserAndGrandPrivileges(user);
        grantUserPrivileges(user);
        mySQLConnection.closeConnection();
    }

    private void grantUserPrivileges(User user) {
        String queryGrantPrivileges = "GRANT SELECT ON *.* TO " + user.getUsername() + "@localhost";
        try(PreparedStatement ps = mySQLConnection.getConnection().prepareStatement(queryGrantPrivileges)) {
            ps.execute();
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private void createUserAndGrandPrivileges(User user) {
        String queryCreateUser = "CREATE USER '" + user.getUsername() + "'@'localhost'" +
            "IDENTIFIED BY '" + password + "'";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(queryCreateUser)) {
            preparedStatement.execute();
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
