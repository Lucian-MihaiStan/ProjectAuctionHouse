package load_data_db.user_db_extract.adapter_lp;

import client.legalperson.LegalPerson;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

/**
 * adapter interface for querying information about legal person type
 */
public interface IAdapterDBLP {
    /**
     * get list of users of leaal person type
     * @param mySQLConnection connection to the database
     * @return list of users of legal person type
     */
    List<LegalPerson> getLPFromDB(MySQLConnection mySQLConnection);
    List<LegalPerson> searchByDataLP(List<Triple<Integer, Double, String>> listDataLP);
}
