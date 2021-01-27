package load_data_db.user_db_extract.adapter_ip;

import client.individualperson.IndividualPerson;
import client.individualperson.IndividualPersonBuilder;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IPersonDBExtract implements IAdapterDBIP {
    private MySQLConnection mySQLConnection;

    @Override
    public List<IndividualPerson> getIPFromDB(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
        List<IndividualPerson> ipList = new ArrayList<>();
        String query = "SELECT * FROM auctionhouse.individualperson";
        List<Pair<Integer, Date>> listDataIP = new ArrayList<>();
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while(resultSet.next()) {
                Pair<Integer, Date> dataIP = loadIndividualPersonData(resultSet);
                listDataIP.add(dataIP);
            }
            ipList = searchByDataIP(listDataIP);
        } catch (SQLException errorSQL) {
            errorSQL.printStackTrace();
        }
        return ipList;
    }

    @Override
    public List<IndividualPerson> searchByDataIP(List<Pair<Integer, Date>> listDataIP) {
        List<IndividualPerson> individualPeople = new ArrayList<>();
        String query = "SELECT * FROM auctionhouse.client WHERE id = ?";
        try(PreparedStatement preparedStatement = mySQLConnection.getConnection().prepareStatement(query)){
            listDataIP.forEach(iteratorID -> {
                try {
                    preparedStatement.setInt(1, iteratorID.getLeft());
                    ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()) {
                        individualPeople.add(
                                new IndividualPersonBuilder()
                                        .withUsername(rs.getString("username"))
                                        .withEmail(rs.getString("email"))
                                        .withFirstName(rs.getString("first_name"))
                                        .withLastName(rs.getString("last_name"))
                                        .withAddress(rs.getString("address"))
                                        .withNoParticipation(rs.getInt("noParticipation"))
                                        .withWonAction(iteratorID.getLeft())
                                        .withBirthDate(iteratorID.getRight())
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
