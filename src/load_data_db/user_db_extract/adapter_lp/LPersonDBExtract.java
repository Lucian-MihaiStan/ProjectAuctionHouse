package load_data_db.user_db_extract.adapter_lp;

import client.legalperson.LegalPerson;
import client.legalperson.LegalPersonBuilder;
import load_data_db.LoadDBDataAdmin;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LPersonDBExtract implements IAdapterDBLP {
    private MySQLConnection mySQLConnection;

    @Override
    public List<LegalPerson> getLPFromDB(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
        List<LegalPerson> lpList = new ArrayList<>();
        String query = "SELECT * FROM auctionhouse.legalperson";
        List<Triple<Integer, Double, String>> listDataLP = new ArrayList<>();
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while(resultSet.next()) {
                Triple<Integer, Double, String> dataLP = loadLegalPerson(resultSet);
                listDataLP.add(dataLP);
            }
            lpList = searchByDataLP(listDataLP);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return lpList;
    }

    @Override
    public List<LegalPerson> searchByDataLP(List<Triple<Integer, Double, String>> listDataLP) {
        List<LegalPerson> legalPeople = new ArrayList<>();
        String query = "SELECT * FROM auctionhouse.client WHERE id = ?";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            listDataLP.forEach(iteratorIDS -> {
                try {
                    preparedStatement.setInt(1, iteratorIDS.getLeft());
                    ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()) {
                        legalPeople.add(
                                new LegalPersonBuilder()
                                        .withUsername(rs.getString("username"))
                                        .withFirstName(rs.getString("first_name"))
                                        .withLastName(rs.getString("last_name"))
                                        .withAddress(rs.getString("address"))
                                        .withNoParticipation(rs.getInt("noParticipation"))
                                        .withWonAction(rs.getInt("noAuctionsWon"))
                                        .withSocialCapital(iteratorIDS.getMiddle())
                                        .withTypeCompany(LegalPerson.TypeCompany.valueOf(iteratorIDS.getRight()))
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

        return legalPeople;
    }

    private Triple<Integer, Double, String> loadLegalPerson(ResultSet resultSet) {
        Triple<Integer, Double, String> dataLP = null;
        try {
            dataLP = new ImmutableTriple<>(
                    resultSet.getInt("id_legalPerson"),
                    resultSet.getDouble("socialCapital"),
                    resultSet.getString("company"));
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return dataLP;
    }
}
