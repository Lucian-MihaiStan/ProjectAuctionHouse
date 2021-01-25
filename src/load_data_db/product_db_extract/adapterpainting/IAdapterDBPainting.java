package load_data_db.product_db_extract.adapterpainting;

import loginsql.MySQLConnection;
import org.apache.commons.lang3.tuple.Triple;
import products.painting.Painting;

import java.util.List;

public interface IAdapterDBPainting {
    List<Painting> getPaintingFromDB(MySQLConnection mySQLConnection);
    List<Painting> searchByDataPainting(List<Triple<Integer, String, Painting.Colors>> listDataPainting);
}
