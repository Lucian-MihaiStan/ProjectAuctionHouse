package load_data_db.product_db_extract.adapterjewellery;

import load_data_db.LoadDBDataAdmin;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import products.jewellery.Jewellery;
import products.jewellery.JewelleryBuilder;
import socketserver.ServerClientThread;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBJewellery implements IAdapterDBJewellery {
    private MySQLConnection mySQLConnection;
    @Override
    public List<Jewellery> getJewelleryFromDB(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
        List<Jewellery> jewelleryList = new ArrayList<>();
        String query = "SELECT * FROM auctionhouseproduct.jewellery";
        List<Triple<Integer, String, Boolean>> listDataJewellery = new ArrayList<>();
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while(resultSet.next()) {
                Triple<Integer, String, Boolean> dataJewellery = loadJewellery(resultSet);
                listDataJewellery.add(dataJewellery);
            }
            jewelleryList = searchByDataJewellery(listDataJewellery);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return jewelleryList;
    }

    @Override
    public List<Jewellery> searchByDataJewellery(List<Triple<Integer, String, Boolean>> listDataJewellery) {
        List<Jewellery> jewelleryList = new ArrayList<>();
        String query = "SELECT * FROM auctionhouseproduct.product WHERE id = ?";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            listDataJewellery.forEach(iteratorISB -> {
                try {
                    preparedStatement.setInt(1, iteratorISB.getLeft());
                    ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()) {
                        jewelleryList.add(
                                new JewelleryBuilder()
                                        .withId(rs.getInt("id"))
                                        .withName(rs.getString("name"))
                                        .withSellingPrice(rs.getDouble("sellingPrice"))
                                        .withMinimPrice(rs.getDouble("minimumPrice"))
                                        .withYear(rs.getInt("year"))
                                        .withMaterial(iteratorISB.getMiddle())
                                        .withGemstone(iteratorISB.getRight())
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
        return jewelleryList;
    }

    private Triple<Integer, String, Boolean> loadJewellery(ResultSet resultSet) {
        Triple<Integer, String, Boolean> dataFurniture = null;
        try {
            dataFurniture = new ImmutableTriple<>(
                    resultSet.getInt("id_jewellery"),
                    resultSet.getString("material"),
                    resultSet.getBoolean("gemstone"));
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return dataFurniture;
    }
}
