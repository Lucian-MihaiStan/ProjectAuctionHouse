package commander.deleteproduct;

import loginsql.MySQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteFromDB {
    private DeleteFromDB() {}

    private static final MySQLConnection mySqlConnection = MySQLConnection.getInstance();

    protected static void deleteFromDB(int productId) {
        int productType = getProductType(productId);
        String deleteFromSubClass = buildQueryString(productId, productType);

        deleteFromSubTable(deleteFromSubClass);

        deleteFromProductTable(productId);
    }

    private static void deleteFromProductTable(int productId) {
        String deleteQuery = "DELETE FROM auctionhouseproduct.product WHERE id = " + productId;
        try(PreparedStatement preparedStatement = mySqlConnection.getConnection().prepareStatement(deleteQuery)) {
            preparedStatement.execute();
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private static void deleteFromSubTable(String deleteFromSubClass) {
        try(PreparedStatement preparedStatement = mySqlConnection.getConnection().prepareStatement(deleteFromSubClass)){
            preparedStatement.execute();
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private static String buildQueryString(int productId, int productType) {
        String deleteFromSubClass = "DELETE FROM auctionhouseproduct.";
        switch (productType) {
            case 1 -> deleteFromSubClass += "painting ";
            case 2 -> deleteFromSubClass += "furniture ";
            case 3 -> deleteFromSubClass += "jewellery ";
        }
        deleteFromSubClass += "WHERE id_";
        switch (productType) {
            case 1 -> deleteFromSubClass += "painting =";
            case 2 -> deleteFromSubClass += "furniture =";
            case 3 -> deleteFromSubClass += "jewellery =";
        }
        deleteFromSubClass += productId;
        return deleteFromSubClass;
    }

    private static int getProductType(int productId) {
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
