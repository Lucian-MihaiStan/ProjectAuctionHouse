package load_data_db;

import client.User;
import load_data_db.user_db_extract.adapter_lp.AdapterDataLP;
import load_data_db.user_db_extract.adapter_lp.IAdapterDataLP;
import load_data_db.user_db_extract.adapter_ip.AdapterDataIP;
import load_data_db.user_db_extract.adapter_ip.IAdapterDataIP;
import loginsql.MySQLConnection;
import products.Product;

import java.sql.SQLException;
import java.util.*;

public class LoadDataAdmin implements IAdapterAdmin {
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
                "clients", Collections.singletonList(userListDB)
        );
        mapUsers.put(
                "products", Collections.singletonList(productListDB)
        );
//        mapUsers.put(
//              "auctions", Collections.singletonList(auctionsDB)
//        );
        return mapUsers;
    }

    private List<Product> extractProductsDB() {
        List<Product> productListDB = new ArrayList<>();

        return productListDB;
    }

    private List<User> extractUsersDB() {
        List<User> userListDB = new ArrayList<>();
        IAdapterDataLP adapterDataLP = new AdapterDataLP();
        IAdapterDataIP adapterDataIP = new AdapterDataIP();

        userListDB.addAll(adapterDataIP.getIPFromDB());
        userListDB.addAll(adapterDataLP.getLPFromDB());

        return userListDB;
    }

    private List<Product> getPFromDB() {
        return null;
    }
}
