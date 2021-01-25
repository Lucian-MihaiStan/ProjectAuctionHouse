package loginsql;

import loginsql.clientconnection.TableNamesClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConnection {
    private static MySQLConnection instance;
    private Connection connection;
    private String username;
    private String password;

    public static synchronized MySQLConnection getInstance() {
        if(instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }

    private MySQLConnection() {
        //
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (NullPointerException nullException) {
            //
        }
    }

    public void realizeConnection(String username, String password) throws SQLException, ClassNotFoundException {
        this.closeConnection();
        this.username = username;
        this.password = password;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String jdbcURL = "jdbc:mysql://localhost:3306/AuctionHouse";
        connection = DriverManager.getConnection(jdbcURL, username, password);
    }

    public void realizeConnectionAsAdmin() throws SQLException, ClassNotFoundException{
        this.closeConnection();
        Class.forName("com.mysql.cj.jdbc.Driver");
        String jdbcURL = "jdbc:mysql://localhost:3306/AuctionHouse";
        connection = DriverManager.getConnection(jdbcURL, "admin", "admin");
    }

    public synchronized String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void cleanUpTables() {
        for (TableNamesClient value : TableNamesClient.values()) {
            String tableName = String.valueOf(value).toLowerCase().replace("_", "");
            String query = "DELETE FROM " + tableName;
            String queryId = "ALTER TABLE " + tableName + " auto_increment=1";
            try(PreparedStatement preparedStatementDelete = connection.prepareStatement(query);
            PreparedStatement preparedStatementSetId = connection.prepareStatement(queryId)){
                preparedStatementDelete.execute();
                preparedStatementSetId.execute();
            } catch (SQLException errorSQL) {
                errorSQL.printStackTrace();
            }
        }
    }

}
