package load_data_db.user_db_extract.adapter_ip;

import client.individualperson.IndividualPerson;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Date;
import java.util.List;

public interface IAdapterDataIP {
    List<IndividualPerson> getIPFromDB();
    List<IndividualPerson> searchByDataIP(List<Pair<Integer, Date>> listDataIP);
}
