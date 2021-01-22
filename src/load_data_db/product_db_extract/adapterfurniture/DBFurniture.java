package load_data_db.product_db_extract.adapterfurniture;

import load_data_db.LoadDBDataAdmin;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import products.furniture.Furniture;
import products.furniture.FurnitureBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBFurniture implements IAdapterDBFurniture {
    private static final MySQLConnection mySQLConnection = LoadDBDataAdmin.mySQLConnection;

    @Override
    public List<Furniture> getFurnitureFromDB() {
        List<Furniture> furnitureList = new ArrayList<>();
        String query = "SELECT * FROM auctionhouseproduct.furniture";
        List<Triple<Integer, String, String>> listDataFurniture = new ArrayList<>();
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while(resultSet.next()) {
                Triple<Integer, String, String> dataFurniture = loadFurniture(resultSet);
                listDataFurniture.add(dataFurniture);
            }
            furnitureList = searchByDataFurniture(listDataFurniture);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return furnitureList;
    }

    @Override
    public List<Furniture> searchByDataFurniture(List<Triple<Integer, String, String>> listDataFurniture) {
        List<Furniture> furnitureList = new ArrayList<>();
        String query = "SELECT * FROM auctionhouseproduct.furniture WHERE id = ?";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            listDataFurniture.forEach(integerDoubleStringTriple -> {
                try {
                    preparedStatement.setInt(1, integerDoubleStringTriple.getLeft());
                    ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()) {
                        furnitureList.add(
                                new FurnitureBuilder()
                                        .withName(rs.getString("name"))
                                        .withSellingPrice(rs.getDouble("sellingPrice"))
                                        .withMinimPrice(rs.getDouble("minimumPrice"))
                                        .withYear(rs.getInt("year"))
                                        .withType(rs.getString("type"))
                                        .withMaterial(rs.getString("material"))
                                        .build()
                        );
                    }
                    preparedStatement.clearParameters();
                } catch (SQLException errorSQL) {
                    errorSQL.printStackTrace();
                }
            });
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return furnitureList;
    }

    private Triple<Integer, String, String> loadFurniture(ResultSet resultSet) {
        Triple<Integer, String, String> dataFurniture = null;
        try {
            dataFurniture = new ImmutableTriple<>(
                    resultSet.getInt("id_furniture"),
                    resultSet.getString("type"),
                    resultSet.getString("material"));
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return dataFurniture;
    }
}
