package load_data_db.product_db_extract.adapterjewellery;

import load_data_db.product_db_extract.adapterfurniture.IAdapterDBFurniture;
import org.apache.commons.lang3.tuple.Triple;
import products.furniture.Furniture;

import java.util.List;

public class DBJewellery implements IAdapterDBFurniture {
    @Override
    public List<Furniture> getFurnitureFromDB() {
        return null;
    }

    @Override
    public List<Furniture> searchByDataFurniture(List<Triple<Integer, String, String>> listDataFurniture) {
        return null;
    }
}
