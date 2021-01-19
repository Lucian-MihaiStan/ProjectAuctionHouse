package loginsql.product_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionProduct {
    private static MySQLConnectionProduct instance;
    private Connection connection;

    public static MySQLConnectionProduct getInstance() {
        if(instance == null) {
            instance = new MySQLConnectionProduct();
        }
        return instance;
    }

    private MySQLConnectionProduct() {
        try{
            Class.forName("com.mysql.cj.jdbc.Drive");
            String jdbcURL = "jdbc:mysql://localhost:3306/AuctionHouseProduct";
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
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }
}
