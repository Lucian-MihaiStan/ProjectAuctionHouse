package loginsql;

import employee.Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private Connection connection;
    private String username;
    private String password;

    public Connection getConnection() {
        return connection;
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
        Admin admin = Admin.getInstance();
        connection = DriverManager.getConnection(jdbcURL, admin.getAdminCredentials(), admin.getAdminCredentials());
    }

    public synchronized String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
