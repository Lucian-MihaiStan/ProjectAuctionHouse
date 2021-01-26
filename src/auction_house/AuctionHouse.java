package auction_house;

import client.User;
import employee.Broker;
import load_data_db.LoadDBDataAdmin;
import load_data_db.IAdapterAdmin;
import auction.Auction;
import loginsql.MySQLConnection;
import products.Product;
import socketserver.Main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuctionHouse {
    private static AuctionHouse instance;

    private List<Product> productsList;
    private List<User> userList;
    private List<Auction> auctionsActive;
    private List<Broker> brokersList;

    public static synchronized AuctionHouse getInstance() {
        if(instance == null) {
            instance = new AuctionHouse();
        }
        return instance;
    }

    private AuctionHouse() {
        productsList = new ArrayList<>();
        userList = new ArrayList<>();
        auctionsActive = new ArrayList<>();
        brokersList = new ArrayList<>();
    }

    public void notifyBrokers(Auction auction) {
        auction.getUsernames().forEach(user -> {
            int idBroker = Main.random.nextInt(this.brokersList.size());
            Broker broker = brokersList.get(idBroker);
            broker.getAuctionsList().add(auction);
            broker.getClientsUsernames().add(user);
        });
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Auction> getAuctionsActive() {
        return auctionsActive;
    }

    public void setAuctionsActive(List<Auction> auctionsActive) {
        this.auctionsActive = auctionsActive;
    }

    public void addNewClient(User user) {
        userList.add(user);
    }

    public User getLastClient() {
        return userList.get(userList.size() - 1);
    }

    public void addNewProduct(Product product) {
        productsList.add(product);
    }

    public Product getLastProduct() {
        return productsList.get(productsList.size() - 1);
    }

    public List<Broker> getBrokersList() {
        return brokersList;
    }

    public void setBrokersList(List<Broker> brokersList) {
        this.brokersList = brokersList;
    }

    public void load(MySQLConnection mySQLConnection) {
        IAdapterAdmin adapter = new LoadDBDataAdmin(mySQLConnection);
        userList = new ArrayList<>();
        productsList = new ArrayList<>();
        Map<String, List<?>> auctionHouseData = adapter.connectToDatabaseAsAdmin().extractFromDatabase();
        try {
            if(mySQLConnection.getUsername() != null) {
                mySQLConnection.realizeConnection(
                        mySQLConnection.getUsername(),
                        mySQLConnection.getPassword()
                );
            }
        } catch (SQLException | ClassNotFoundException errorSQL) {
            errorSQL.printStackTrace();
        }

        try{
            auctionHouseData.get("users").forEach(
                    user -> userList.add((User) user)
            );
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        try{
            auctionHouseData.get("products").forEach(
                    product -> productsList.add((Product) product)
            );
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        if(brokersList.isEmpty()) generateBrokers();
        if(auctionsActive.isEmpty()) createAuctions();
        if(auctionsActive.size() < productsList.size()) {
            int id = 0;
            for (Product product : productsList)
                if(product.getId() > id) id = product.getId();
            updateAuctions(id);
        }
    }

    private void updateAuctions(int productId) {
        auctionsActive.add(
                new Auction(
                        productId,
                        0,
                        Main.random.nextInt(5) + 1,
                        productId,
                        Main.random.nextInt(5) + 1,
                        new ArrayList<>(),
                        new ArrayList<>()
                )
        );
    }

    private void generateBrokers() {
        int noBrokers = Main.random.nextInt(5) + 2;
        for(int i = 0; i < noBrokers; i++) {
            Broker broker = new Broker(i);
            brokersList.add(broker);
        }
    }

    public void createAuctions() {
        productsList.forEach(
                product -> {
                    Auction auction = new Auction(
                            product.getId(),
                            0,
                            Main.random.nextInt(5) + 1,
                            product.getId(),
                            Main.random.nextInt(5) + 1,
                            new ArrayList<>(),
                            new ArrayList<>()
                            );
                    auctionsActive.add(auction);
                }
        );
    }

    @Override
    public String toString() {
        return "AuctionHouse{" +
                "productsList=" + productsList +
                ", userList=" + userList +
                ", auctionsActive= \n\n" + auctionsActive +
                "\n\n, brokersList=" + brokersList +
                '}';
    }
}
