package load_data_db.user_db_extract.adapter_lp;

import client.legalperson.LegalPerson;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public interface IAdapterDBLP {
    List<LegalPerson> getLPFromDB();
    List<LegalPerson> searchByDataLP(List<Triple<Integer, Double, String>> listDataLP);
}
