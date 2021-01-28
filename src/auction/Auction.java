package auction;

import auction.notifieradapter.INotifierMail;
import auction.notifieradapter.NotifierMailAdapter;
import auction_house.AuctionHouse;
import client.User;
import employee.Broker;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import products.Product;
import socketserver.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Auction {
    private int productId;
    private int noCurrentParticipants;
    private int noParticipants;
    private int idAuction;
    private int noMaxSteps;

    private int maxBid;

    public int getNoCurrentParticipants() {
        return noCurrentParticipants;
    }

    public void setNoCurrentParticipants(int noCurrentParticipants) {
        this.noCurrentParticipants = noCurrentParticipants;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setIdAuction(int idAuction) {
        this.idAuction = idAuction;
    }

    public void setNoMaxSteps(int noMaxSteps) {
        this.noMaxSteps = noMaxSteps;
    }

    public void setNoParticipants(int noParticipants) {
        this.noParticipants = noParticipants;
    }


    @Override
    public String toString() {
        return "Auction{" +
                "productId=" + productId +
                ", noCurrentParticipants=" + noCurrentParticipants +
                ", noParticipants=" + noParticipants +
                ", idAuction=" + idAuction +
                ", noMaxSteps=" + noMaxSteps +
                '}';
    }

    public int getIdAuction() {
        return idAuction;
    }

    public int getNoParticipants() {
        return noParticipants;
    }

    public int getProductId() {
        return productId;
    }

    public void notifyUsers(Map<Integer, Broker> brokers) {
        Product productInfo = AuctionHouse.getInstance()
                .getProductsList()
                .stream()
                .filter(product -> product.getId() == productId)
                .collect(Collectors.toList()).get(0);

        String notify = "Auction for product " + productInfo.toString() + " has started please join at the table";

        List<String> usersEnrolled = new ArrayList<>();

        brokers.forEach((integer, broker) -> {
            if(broker.getAuctionAndUserAssigned().containsKey(idAuction)) {
                broker.getAuctionAndUserAssigned().get(idAuction).forEach((user, bid) -> usersEnrolled.add(user));
            }
        });

        usersEnrolled.forEach(
                username -> {
                    User user = AuctionHouse.getInstance().getUserList().stream().filter(
                            userIt -> username.equals(userIt.getUsername())
                    ).collect(Collectors.toList()).get(0);
                    String email = user.getEmail();
                    INotifierMail iNotifierMail = new NotifierMailAdapter();
                    iNotifierMail.sendEmail(email, notify);
                }
        );
    }

    public synchronized void start(Map<Integer, Broker> brokers, List<User> userList) {

        Product productInfo = AuctionHouse.getInstance().getProductsList()
                .stream().filter(product -> product.getId() == productId).collect(Collectors.toList()).get(0);
        double minimumBid = productInfo.getMinimumPrice();

        Map<Broker, List<Pair<User, Double>>> brokersAndClients = new HashMap<>();
        brokers.forEach((integer, broker) -> {
            if(broker.getAuctionAndUserAssigned().containsKey(idAuction)) {
                Map<String, Double> usersAndBids = broker.getAuctionAndUserAssigned().get(idAuction);
                if (!brokersAndClients.containsKey(broker)) brokersAndClients.put(broker, new ArrayList<>());
                usersAndBids.forEach((username, bid) -> {
                    User user = userList.stream().filter(userIt -> userIt.getUsername().equals(username))
                            .collect(Collectors.toList()).get(0);
                    brokersAndClients.get(broker).add(new ImmutablePair<>(user, bid));
                });
            }
        });
        System.out.println(brokersAndClients);
        List<Double> currentBids;
        for(int i = 0; i < noMaxSteps; i++) {
            currentBids = new ArrayList<>();
            brokersAndClients.forEach((broker, userAndMaxBid) -> {
                int randomStrategy = Main.random.nextInt(3) + 1;
            });
        }
    }
}
