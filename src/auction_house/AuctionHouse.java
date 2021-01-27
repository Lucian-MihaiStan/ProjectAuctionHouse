package auction_house;

import auction.AuctionBuilder;
import auction.notifieradapter.INotifierMail;
import auction.notifieradapter.NotifierMailAdapter;
import client.User;
import employee.Broker;
import load_data_db.LoadDBDataAdmin;
import load_data_db.IAdapterAdmin;
import auction.Auction;
import loginsql.MySQLConnection;
import products.Product;
import socketserver.Main;

import java.sql.SQLException;
import java.util.*;

public class AuctionHouse {
    private static AuctionHouse instance;

    private List<Product> productsList;
    private List<User> userList;
    private List<Auction> auctionsActive;
    private List<Broker> brokersList;

    static final int NO_MAX_BROKERS = 6;

    public static synchronized AuctionHouse getInstance() {
        if (instance == null) {
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
        Map<Integer, List<String>> mapBrokers = new HashMap<>();
        auction.getUsernames().forEach(user -> {
            int idBroker = Main.random.nextInt(this.brokersList.size());
            if (!mapBrokers.containsKey(idBroker)) {
                mapBrokers.put(idBroker, new ArrayList<>());
            }
            mapBrokers.get(idBroker).add(user);
            Broker broker = brokersList.get(idBroker);
            broker.getAuctionsList().add(auction);
            broker.getClientsUsernames().add(user);
        });

//        INotifierMail notifierBrokers = new NotifierMailAdapter();
//        notifierBrokers.sendMailToBrokers(mapBrokers, auction,
//                productsList.stream().filter(product -> product.getId() == auction.getProductId())
//                        .collect(Collectors.toList()).get(0));

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
            if (mySQLConnection.getUsername() != null) {
                mySQLConnection.realizeConnection(
                        mySQLConnection.getUsername(),
                        mySQLConnection.getPassword()
                );
            }
        } catch (SQLException | ClassNotFoundException errorSQL) {
            errorSQL.printStackTrace();
        }

        auctionHouseData.get("users").forEach(
                user -> userList.add((User) user)
        );
        auctionHouseData.get("products").forEach(
                product -> productsList.add((Product) product)
        );

        if (brokersList.isEmpty()) generateBrokers();
        if (auctionsActive.isEmpty()) createAuctions();
        if (auctionsActive.size() < productsList.size()) {
            int id = 0;
            for (Product product : productsList)
                if (product.getId() > id) id = product.getId();
            addNewAuctionToList(id);
        }
    }

    private void addNewAuctionToList(int productId) {
        auctionsActive.add(
                new AuctionBuilder()
                        .withId(productId)
                        .withNoCurrentParticipants(0)
                        .withNoParticipants(Main.random.nextInt(5) + 2)
                        .withProductId(productId)
                        .withNoMaxSteps(Main.random.nextInt(5) + 2)
                        .withUserNames(new ArrayList<>())
                        .withBids(new ArrayList<>())
                        .withMaximumBids(new ArrayList<>())
                        .build()
        );
    }

    private void generateBrokers() {
        int noBrokers = Main.random.nextInt(5) + 1;
        for (int i = 0; i < noBrokers; i++) {
            Broker broker = new Broker(i);
            brokersList.add(broker);
        }
    }

    public void createAuctions() {
        productsList.forEach(product -> addNewAuctionToList(product.getId()));
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
