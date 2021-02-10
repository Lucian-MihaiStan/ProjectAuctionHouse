package load_data_db.product_db_extract.adapterfurniture;

import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.Triple;
import products.furniture.Furniture;

import java.util.List;

/**
 * adapter interface for querying information about furniture product type
 */
public interface IAdapterDBFurniture {
    /**
     * get list of products of furniture type
     * @param mySQLConnection connection to the database
     * @return list of products of furniture type
     */
    List<Furniture> getFurnitureFromDB(MySQLConnection mySQLConnection);
    List<Furniture> searchByDataFurniture(List<Triple<Integer, String, String>> listDataFurniture);
}
