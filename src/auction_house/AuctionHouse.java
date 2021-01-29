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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import products.Product;
import socketserver.Main;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AuctionHouse {
    private static AuctionHouse instance;

    private List<Product> productsList;
    private List<User> userList;
    private Map<Integer, Auction> auctionsActive;
    private final Map<Integer, Broker> brokers;

    static final int NO_MAX_BROKERS = 5;

    public static synchronized AuctionHouse getInstance() {
        if (instance == null) {
            instance = new AuctionHouse();
        }
        return instance;
    }

    private AuctionHouse() {
        productsList = Collections.synchronizedList(new ArrayList<>());
        userList = Collections.synchronizedList(new ArrayList<>());
        auctionsActive = Collections.synchronizedMap(new HashMap<>());
        brokers = new HashMap<>();
    }

    public void notifyBrokers(int auctionId) {

        Auction auction = auctionsActive.get(auctionId);

        Map<Integer, List<Pair<String, Double>>> mapBrokers = new HashMap<>();

        brokers.forEach((integer, broker) -> {
            if(broker.getAuctionAndUserAssigned().containsKey(auctionId)) {
                if(!mapBrokers.containsKey(broker.getId())) {
                    mapBrokers.put(broker.getId(), new ArrayList<>());
                }
                broker.getAuctionAndUserAssigned().get(auctionId).forEach((username, bid) -> {
                    Pair<String, Double> userInfo = new ImmutablePair<>(username, bid);
                    mapBrokers.get(broker.getId()).add(userInfo);
                });
            }
        });

        INotifierMail notifierBrokers = new NotifierMailAdapter();
        notifierBrokers.sendMailToBrokers(mapBrokers, auction,
                productsList.stream().filter(product -> product.getId() == auctionId)
                        .collect(Collectors.toList()).get(0));

    }

    public Map<Integer, Auction> getAuctionsActive() {
        return auctionsActive;
    }

    public void setAuctionsActive(Map<Integer, Auction> auctionsActive) {
        this.auctionsActive = auctionsActive;
    }

    public Map<Integer, Broker> getBrokers() {
        return brokers;
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

    public void load(MySQLConnection mySQLConnection) {
        IAdapterAdmin adapter = new LoadDBDataAdmin(mySQLConnection);
        userList = Collections.synchronizedList(new ArrayList<>());
        productsList = Collections.synchronizedList(new ArrayList<>());
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

        if (brokers.isEmpty()) generateBrokers();
        if (auctionsActive.isEmpty()) createAuctions();
        if (auctionsActive.size() < productsList.size()) {
            int id = 0;
            for (Product product : productsList)
                if (product.getId() > id) id = product.getId();
            addNewAuctionToList(id);
        }
    }

    private void addNewAuctionToList(int productId) {
        auctionsActive.put(productId, new AuctionBuilder()
                .withId(productId)
                .withNoCurrentParticipants(0)
                .withNoParticipants(Main.random.nextInt(5) + 2)
                .withProductId(productId)
                .withNoMaxSteps(Main.random.nextInt(5) + 2)
                .build());
    }

    private void generateBrokers() {
        int noBrokers = Main.random.nextInt(NO_MAX_BROKERS) + 1;
        for (int i = 0; i < noBrokers; i++) {
            Broker broker = new Broker(i);
            brokers.put(i, broker);
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
                ", auctionsActive=" + auctionsActive +
                ", brokers=" + brokers +
                '}';
    }


    public double calculateMaximumBid(List<Double> currentBids) {
        return Collections.max(currentBids);
    }
}
