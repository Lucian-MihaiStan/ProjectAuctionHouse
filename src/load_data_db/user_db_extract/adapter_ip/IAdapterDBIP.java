package load_data_db.user_db_extract.adapter_ip;

import client.individualperson.IndividualPerson;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Date;
import java.util.List;

/**
 * adapter interface for querying information about individual person type
 */
public interface IAdapterDBIP {
    /**
     * get list of users of individual person type
     * @param mySQLConnection connection to the database
     * @return list of users of individual person type
     */
    List<IndividualPerson> getIPFromDB(MySQLConnection mySQLConnection);
    List<IndividualPerson> searchByDataIP(List<Pair<Integer, Date>> listDataIP);
}
