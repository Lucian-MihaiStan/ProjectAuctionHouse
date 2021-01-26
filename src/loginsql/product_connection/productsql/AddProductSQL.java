package loginsql.product_connection.productsql;

import loginsql.MySQLConnection;
import products.Product;
import products.furniture.Furniture;
import products.jewellery.Jewellery;
import products.painting.Painting;
import socketserver.ServerClientThread;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProductSQL {
    private final MySQLConnection mySQLConnection;
    private static final String QUERY_CONSTANT = " VALUES ((SELECT id FROM auctionhouseproduct.product WHERE id = (SELECT MAX(id) FROM auctionhouseproduct.product)), ?, ?)";
    public AddProductSQL(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }

    public void addProductSQL(Product product) {
        int typeProduct;
        if(product instanceof Painting) typeProduct = 1;
        else if(product instanceof Furniture) typeProduct = 2;
        else typeProduct = 3;
        String query = "INSERT INTO auctionhouseproduct.product (productType, name, sellingPrice, minimumPrice, year)" +
                " values(?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, typeProduct);
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getSellingPrice());
            preparedStatement.setDouble(4, product.getMinimumPrice());
            preparedStatement.setInt(5, product.getYear());

            preparedStatement.execute();

            if(product instanceof Painting) addProductPainting((Painting) product);
            else if(product instanceof Furniture) addProductFurniture((Furniture) product);
            else addProductJewellery((Jewellery) product);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private void addProductJewellery(Jewellery product) {
        String query = "INSERT INTO auctionhouseproduct.jewellery (id_jewellery, material, gemstone)" + QUERY_CONSTANT;
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, product.getMaterial());
            preparedStatement.setBoolean(2, product.isGemstone());

            preparedStatement.execute();
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private void addProductFurniture(Furniture product) {
        String query = "INSERT INTO auctionhouseproduct.furniture (id_furniture, type, material)" + QUERY_CONSTANT;
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, product.getType());
            preparedStatement.setString(2, product.getMaterial());

            preparedStatement.execute();
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }

    private void addProductPainting(Painting product) {
        String query = "INSERT INTO auctionhouseproduct.painting (id_painting, name_artist, color)" + QUERY_CONSTANT;
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, product.getNameArtist());
            preparedStatement.setString(2, String.valueOf(product.getColors()));

            preparedStatement.execute();
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
    }
}
