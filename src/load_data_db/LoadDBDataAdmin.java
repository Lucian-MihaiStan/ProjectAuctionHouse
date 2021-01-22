package load_data_db;

import client.User;
import load_data_db.product_db_extract.adapterfurniture.DBFurniture;
import load_data_db.product_db_extract.adapterfurniture.IAdapterDBFurniture;
import load_data_db.product_db_extract.adapterjewellery.DBJewellery;
import load_data_db.product_db_extract.adapterjewellery.IAdapterDBJewellery;
import load_data_db.product_db_extract.adapterpainting.DBPainting;
import load_data_db.product_db_extract.adapterpainting.IAdapterDBPainting;
import load_data_db.user_db_extract.adapter_lp.LPersonDBExtract;
import load_data_db.user_db_extract.adapter_lp.IAdapterDBLP;
import load_data_db.user_db_extract.adapter_ip.IPersonDBExtract;
import load_data_db.user_db_extract.adapter_ip.IAdapterDBIP;
import loginsql.MySQLConnection;
import products.Product;

import java.sql.SQLException;
import java.util.*;

public class LoadDBDataAdmin implements IAdapterAdmin {
    public static final MySQLConnection mySQLConnection = MySQLConnection.getInstance();

    @Override
    public IAdapterAdmin connectToDatabaseAsAdmin() {
        try {
            mySQLConnection.realizeConnection("admin", "admin");
        } catch (SQLException | ClassNotFoundException errorSQL) {
            errorSQL.printStackTrace();
        }
        return this;
    }

    @Override
    public Map<String, List<Object>> extractFromDatabase() {
        Map<String, List<Object>> mapUsers = new HashMap<>();

        List<User> userListDB = extractUsersDB();
        List<Product> productListDB = extractProductsDB();

        mapUsers.put(
                "users", Collections.singletonList(userListDB)
        );
        mapUsers.put(
                "products", Collections.singletonList(productListDB)
        );
        System.out.println(userListDB);
//        mapUsers.put(
//              "auctions", Collections.singletonList(auctionsDB)
//        );
        return mapUsers;
    }

    private List<Product> extractProductsDB() {
        List<Product> productListDB = new ArrayList<>();
        IAdapterDBPainting adapterDBPainting = new DBPainting();
        IAdapterDBJewellery adapterDBJewellery = new DBJewellery();
        IAdapterDBFurniture adapterDBFurniture = new DBFurniture();

        productListDB.addAll(adapterDBFurniture.getFurnitureFromDB());
        productListDB.addAll(adapterDBJewellery.getJewelleryFromDB());
        productListDB.addAll(adapterDBPainting.getPaintingFromDB());
        return productListDB;
    }

    private List<User> extractUsersDB() {
        List<User> userListDB = new ArrayList<>();
        IAdapterDBLP adapterDataLP = new LPersonDBExtract();
        IAdapterDBIP adapterDataIP = new IPersonDBExtract();

        userListDB.addAll(adapterDataIP.getIPFromDB());
        userListDB.addAll(adapterDataLP.getLPFromDB());

        return userListDB;
    }
}
