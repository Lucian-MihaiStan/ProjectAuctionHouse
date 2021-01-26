package commander.deleteproduct;

import loginsql.MySQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteFromDB {
    private DeleteFromDB() {}

    protected static void deleteFromDB(int productId, MySQLConnection mySqlConnection) {
        int productType = getProductType(productId, mySqlConnection);
        String deleteFromSubClass = buildQueryString(productId, productType);

        deleteFromSubTable(deleteFromSubClass, mySqlConnection);

        deleteFromProductTable(productId, mySqlConnection);
    }

    private static void deleteFromProductTable(int productId, MySQLConnection mySqlConnection) {

        String deleteQuery = "DELETE FROM auctionhouseproduct.product WHERE id = " + productId;
        try(PreparedStatement preparedStatement = mySqlConnection.getConnection().prepareStatement(deleteQuery)) {
            preparedStatement.execute();
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private static void deleteFromSubTable(String deleteFromSubClass, MySQLConnection mySqlConnection) {
        try(PreparedStatement preparedStatement = mySqlConnection.getConnection().prepareStatement(deleteFromSubClass)){
            preparedStatement.execute();
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private static String buildQueryString(int productId, int productType) {
        String deleteFromSubClass = "DELETE FROM auctionhouseproduct.";
        if (productType == 1) {
            deleteFromSubClass += "painting ";
        } else if (productType == 2) {
            deleteFromSubClass += "furniture ";
        } else if (productType == 3) {
            deleteFromSubClass += "jewellery ";
        }
        deleteFromSubClass += "WHERE id_";
        if (productType == 1) {
            deleteFromSubClass += "painting =";
        } else if (productType == 2) {
            deleteFromSubClass += "furniture =";
        } else if (productType == 3) {
            deleteFromSubClass += "jewellery =";
        }
        deleteFromSubClass += productId;
        return deleteFromSubClass;
    }

    private static int getProductType(int productId, MySQLConnection mySqlConnection) {
        String query = "SELECT * FROM auctionhouseproduct.product WHERE id = " + productId;
        int productType = 0;
        try(PreparedStatement preparedStatement = mySqlConnection.getConnection().prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while(resultSet.next()) {
                productType = resultSet.getInt("productType");
            }
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return productType;
    }
}
