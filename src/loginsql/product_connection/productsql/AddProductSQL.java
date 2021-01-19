package loginsql.product_connection.productsql;

import client.Client;
import loginsql.product_connection.MySQLConnectionProduct;
import main.Main;
import products.Product;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProductSQL {
    MySQLConnectionProduct mySQLConnectionProduct = Main.mySQLConnectionProduct;

    public void addProductSQL(Product product) {
        String query = "INSERT INTO product ()";

        try(PreparedStatement preparedStatement = mySQLConnectionProduct.getConnection().prepareStatement(query)) {

        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }
}
