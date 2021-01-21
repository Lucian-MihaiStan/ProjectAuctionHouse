package loginsql;

import loginsql.clientconnection.TableNamesClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConnection {
    private static MySQLConnection instance;
    private Connection connection;

    public static MySQLConnection getInstance() {
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException sqlError) {
            sqlError.printStackTrace();
        }
    }

    public void realizeConnection(String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String jdbcURL = "jdbc:mysql://localhost:3306/AuctionHouse";
        System.out.println(username + " " + password);
//        String username = "root";
//        String password = "lucian2000";
        connection = DriverManager.getConnection(jdbcURL, username, password);
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
