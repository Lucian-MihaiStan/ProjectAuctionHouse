package load_data_db.product_db_extract.adapterfurniture;

import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.Triple;
import products.furniture.Furniture;

import java.util.List;

public interface IAdapterDBFurniture {
    List<Furniture> getFurnitureFromDB(MySQLConnection mySQLConnection);
    List<Furniture> searchByDataFurniture(List<Triple<Integer, String, String>> listDataFurniture);
}
