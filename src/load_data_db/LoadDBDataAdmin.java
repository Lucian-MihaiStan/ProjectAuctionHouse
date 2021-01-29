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
import socketserver.ServerClientThread;

import java.sql.SQLException;
import java.util.*;

public class LoadDBDataAdmin implements IAdapterAdmin {
    private final MySQLConnection mySQLConnection;

    public LoadDBDataAdmin(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }

    @Override
    public synchronized IAdapterAdmin connectToDatabaseAsAdmin() {
        try {
            mySQLConnection.realizeConnectionAsAdmin();
        } catch (SQLException | ClassNotFoundException errorSQL) {
            errorSQL.printStackTrace();
        }
        return this;
    }

    @Override
    public synchronized Map<String, List<?>> extractFromDatabase() {
        Map<String, List<?>> mapData = new HashMap<>();

        List<User> userListDB = extractUsersDB();
        List<Product> productListDB = extractProductsDB();

        mapData.put(
                "users", userListDB
        );
        mapData.put(
                "products", productListDB
        );
        mySQLConnection.closeConnection();
        return mapData;
    }

    private List<Product> extractProductsDB() {
        List<Product> productListDB = new ArrayList<>();
        IAdapterDBPainting adapterDBPainting = new DBPainting();
        IAdapterDBJewellery adapterDBJewellery = new DBJewellery();
        IAdapterDBFurniture adapterDBFurniture = new DBFurniture();

        productListDB.addAll(adapterDBFurniture.getFurnitureFromDB(mySQLConnection));
        productListDB.addAll(adapterDBJewellery.getJewelleryFromDB(mySQLConnection));
        productListDB.addAll(adapterDBPainting.getPaintingFromDB(mySQLConnection));
        return productListDB;
    }

    private List<User> extractUsersDB() {
        List<User> userListDB = new ArrayList<>();
        IAdapterDBLP adapterDataLP = new LPersonDBExtract();
        IAdapterDBIP adapterDataIP = new IPersonDBExtract();

        userListDB.addAll(adapterDataIP.getIPFromDB(mySQLConnection));
        userListDB.addAll(adapterDataLP.getLPFromDB(mySQLConnection));

        return userListDB;
    }

    public MySQLConnection getMySQLConnection() {
        return this.mySQLConnection;
    }
}
