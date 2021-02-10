package load_data_db.product_db_extract.adapterpainting;

import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.Triple;
import products.painting.Painting;

import java.util.List;

/**
 * adapter interface for querying information about paiting product type
 */
public interface IAdapterDBPainting {
    /**
     * get list of products of furniture type
     * @param mySQLConnection connection to the database
     * @return list of products of furniture type
     */
    List<Painting> getPaintingFromDB(MySQLConnection mySQLConnection);
    List<Painting> searchByDataPainting(List<Triple<Integer, String, Painting.Colors>> listDataPainting);
}
