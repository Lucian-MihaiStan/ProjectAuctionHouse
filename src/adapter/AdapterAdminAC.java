package adapter;

import client.individualperson.IndividualPerson;
import client.individualperson.IndividualPersonBuilder;
import client.legalperson.LegalPerson;
import client.legalperson.LegalPersonBuilder;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import products.Product;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdapterAdminAC implements IAdapterAdmin {
    private static final MySQLConnection mySQLConnection = MySQLConnection.getInstance();

    @Override
    public IAdapterAdmin connectToDatabaseAsAdmin() {
        try {
            mySQLConnection.realizeConnection("admin", "admin");
        } catch (SQLException | ClassNotFoundException errorSQL) {
            errorSQL.printStackTrace();
        }
        return this;
    }

    @Override
    public Map<String, List<Object>> extractFromDatabase() {
        Map<String, List<Object>> mapClients = new HashMap<>();

        mapClients.put(
                "IP", Collections.singletonList(getIPFromDB())
        );
        mapClients.put(
                "LP", Collections.singletonList(getLPFromDB())
        );
        mapClients.put(
                "P", Collections.singletonList(getPFromDB())
        );
        return mapClients;
    }

    private List<Product> getPFromDB() {
        return null;
    }

    private List<LegalPerson> getLPFromDB() {
        List<LegalPerson> lpList = new ArrayList<>();
        String query = "SELECT * FROM legalperson";
        List<Triple<Integer, Double, String>> listDataLP = new ArrayList<>();
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while(resultSet.next()) {
                Triple<Integer, Double, String> dataLP = loadLegalPerson(resultSet);
                listDataLP.add(dataLP);
            }
            lpList = searchBYIdLegalPerson(listDataLP);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return lpList;
    }

    private List<LegalPerson> searchBYIdLegalPerson(List<Triple<Integer, Double, String>> listDataLP) {
        List<LegalPerson> legalPeople = new ArrayList<>();
        String query = "SELECT * FROM auctionhouse.client WHERE id = ?";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            listDataLP.forEach(integerDoubleStringTriple -> {
                try {
                    preparedStatement.setInt(1, integerDoubleStringTriple.getLeft());
                    ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()) {
                        legalPeople.add(
                                new LegalPersonBuilder()
                                        .withFirstName(rs.getString("first_name"))
                                        .withLastName(rs.getString("last_name"))
                                        .withAddress(rs.getString("address"))
                                        .withNoParticipation(rs.getInt("noParticipation"))
                                        .withWonAction(rs.getInt("noAuctionsWon"))
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

    private List<IndividualPerson> getIPFromDB() {
        List<IndividualPerson> ipList = new ArrayList<>();
        String query = "SELECT * FROM individualperson";
        List<Pair<Integer, Date>> listDataIP = new ArrayList<>();
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while(resultSet.next()) {
                Pair<Integer, Date> dataIP = loadIndividualPersonData(resultSet);
                listDataIP.add(dataIP);
            }
            ipList = searchByIdIndividualPerson(listDataIP);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return ipList;
    }

    private List<IndividualPerson> searchByIdIndividualPerson(List<Pair<Integer, Date>> listDataIP) {
        List<IndividualPerson> individualPeople = new ArrayList<>();
        String query = "SELECT * FROM client WHERE id = ?";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            listDataIP.forEach(integerDateMap -> {
                try {
                    preparedStatement.setInt(1, integerDateMap.getLeft());
                    ResultSet rs = preparedStatement.executeQuery(query);
                    while(rs.next()) {
                        individualPeople.add(
                                new IndividualPersonBuilder()
                                        .withFirstName(rs.getString("first_name"))
                                        .withLastName(rs.getString("last_name"))
                                        .withAddress(rs.getString("address"))
                                        .withNoParticipation(rs.getInt("noParticipation"))
                                        .withWonAction(rs.getInt("noAuctionsWon"))
                                        .build()
                        );
                    }
                } catch (SQLException errorSQL) {
                    errorSQL.printStackTrace();
                }
            });
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return individualPeople;
    }

    private Pair<Integer, Date> loadIndividualPersonData(ResultSet resultSet) {
        Pair<Integer, Date> dataSetLP = null;
        try {
            dataSetLP = new ImmutablePair<>(
                    resultSet.getInt("id_individualPerson"),
                    resultSet.getDate("birthDate")
            );
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return dataSetLP;
    }
}
