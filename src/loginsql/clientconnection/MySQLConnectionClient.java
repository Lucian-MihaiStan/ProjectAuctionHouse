package loginsql.clientconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConnectionClient {
    private static MySQLConnectionClient instance;
    private Connection connection;

    public static MySQLConnectionClient getInstance() {
        if(instance == null) {
            instance = new MySQLConnectionClient();
        }
        return instance;
    }

    private MySQLConnectionClient() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcURL = "jdbc:mysql://localhost:3306/AuctionHouse";
            String username = "root";
            String password = "lucian2000";
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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
