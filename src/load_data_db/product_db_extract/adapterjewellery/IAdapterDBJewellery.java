package load_data_db.product_db_extract.adapterjewellery;

import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.Triple;
import products.jewellery.Jewellery;
import socketserver.ServerClientThread;

import java.util.List;

public interface IAdapterDBJewellery {
    List<Jewellery> getJewelleryFromDB(MySQLConnection mySQLConnection);
    List<Jewellery> searchByDataJewellery(List<Triple<Integer, String, Boolean>> listDataJewellery);
}
