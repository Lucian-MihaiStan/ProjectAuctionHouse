package load_data_db.product_db_extract.adapterjewellery;

import org.apache.commons.lang3.tuple.Triple;
import products.jewellery.Jewellery;

import java.util.List;

public interface IAdapterDBJewellery {
    List<Jewellery> getJewelleryFromDB();
    List<Jewellery> searchByDataJewellery(List<Triple<Integer, String, Boolean>> listDataJewellery);
}
