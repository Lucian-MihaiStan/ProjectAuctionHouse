package load_data_db.product_db_extract.adapterpainting;

import load_data_db.LoadDBDataAdmin;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import products.painting.Painting;
import products.painting.PaintingBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBPainting implements IAdapterDBPainting {
    private MySQLConnection mySQLConnection;

    @Override
    public List<Painting> getPaintingFromDB(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
        List<Painting> paintingList = new ArrayList<>();
        String query = "SELECT * FROM auctionhouseproduct.painting";
        List<Triple<Integer, String, Painting.Colors>> listDataPainting = new ArrayList<>();
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while(resultSet.next()) {
                Triple<Integer, String, Painting.Colors> dataPaiting = loadPainting(resultSet);
                listDataPainting.add(dataPaiting);
            }
            paintingList = searchByDataPainting(listDataPainting);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return paintingList;
    }

    @Override
    public List<Painting> searchByDataPainting(List<Triple<Integer, String, Painting.Colors>> listDataPainting) {
        List<Painting> paintingListList = new ArrayList<>();
        String query = "SELECT * FROM auctionhouseproduct.product WHERE id = ?";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            listDataPainting.forEach(iteraetorISC -> {
                try {
                    preparedStatement.setInt(1, iteraetorISC.getLeft());
                    ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()) {
                        paintingListList.add(
                                new PaintingBuilder()
                                        .withId(rs.getInt("id"))
                                        .withName(rs.getString("name"))
                                        .withSellingPrice(rs.getDouble("sellingPrice"))
                                        .withMinimPrice(rs.getDouble("minimumPrice"))
                                        .withYear(rs.getInt("year"))
                                        .withNameArtist(iteraetorISC.getMiddle())
                                        .withColors(iteraetorISC.getRight())
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
        return paintingListList;    }

    private Triple<Integer, String, Painting.Colors> loadPainting(ResultSet resultSet) {
        Triple<Integer, String, Painting.Colors> dataPainting = null;
        try {
            dataPainting = new ImmutableTriple<>(
                    resultSet.getInt("id_painting"),
                    resultSet.getString("name_artist"),
                    Painting.Colors.valueOf(resultSet.getString("color")));
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return dataPainting;
    }
}
