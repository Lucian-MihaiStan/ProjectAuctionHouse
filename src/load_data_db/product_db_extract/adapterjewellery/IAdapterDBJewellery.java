package load_data_db.product_db_extract.adapterjewellery;

import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.Triple;
import products.jewellery.Jewellery;
import socketserver.ServerClientThread;

import java.util.List;

/**
 * adapter interface for querying information about jewellery product type
 */
public interface IAdapterDBJewellery {
    /**
     * get list of products of furniture type
     * @param mySQLConnection connection to the database
     * @return list of products of furniture type
     */
    List<Jewellery> getJewelleryFromDB(MySQLConnection mySQLConnection);
    List<Jewellery> searchByDataJewellery(List<Triple<Integer, String, Boolean>> listDataJewellery);
}
