package load_data_db.user_db_extract.adapter_ip;

import client.individualperson.IndividualPerson;
import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Date;
import java.util.List;

public interface IAdapterDBIP {
    List<IndividualPerson> getIPFromDB(MySQLConnection mySQLConnection);
    List<IndividualPerson> searchByDataIP(List<Pair<Integer, Date>> listDataIP);
}
