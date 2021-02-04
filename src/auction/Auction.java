package auction;

import auction.notifieradapter.INotifierMail;
import auction.notifieradapter.NotifierMailAdapter;
import auction.updatedata.UpdateDataDBAfterAuction;
import auction_house.AuctionHouse;
import client.User;
import employee.Broker;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import products.Product;
import socketserver.Main;
import strategy.*;

import java.util.*;
import java.util.stream.Collectors;

public class Auction {
    private int productId;
    private int noCurrentParticipants;
    private int noParticipants;
    private int idAuction;
    private int noMaxSteps;

    private double maxBid;
    private double minBid;

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

    public synchronized void notifyParticipantsAuctionStart(Map<Integer, Broker> brokers) {
        Product productInfo = AuctionHouse.getInstance()
                .getProductsList()
                .stream()
                .filter(product -> product.getId() == productId)
                .collect(Collectors.toList()).get(0);

        String notify = "Auction for product " + productInfo.toString() + " has started please join at the table";

        List<String> usersEnrolled = new ArrayList<>();

        brokers.forEach((integer, broker) -> {
            if (broker.getAuctionAndUserAssigned().containsKey(idAuction)) {
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

//        notifyParticipantsAuctionStart(brokers);

        Product productInfo = AuctionHouse.getInstance().getProductsList()
                .stream().filter(product -> product.getId() == productId).collect(Collectors.toList()).get(0);
        double minimumBid = productInfo.getMinimumPrice();

        Pair<List<User>, Map<Broker, List<Pair<User, Double>>>> brokersAndClientsAssigned = getBrokerAndClients(brokers, userList);

        List<User> clientsParticipating = brokersAndClientsAssigned.getLeft();
        Map<Broker, List<Pair<User, Double>>> brokersAndClients = brokersAndClientsAssigned.getRight();

        maxBid = minimumBid;
        minBid = minimumBid;

        User winner = mechanismAuction(brokersAndClients, clientsParticipating);


        UpdateDataDBAfterAuction updateDataDBAfterAuction = new UpdateDataDBAfterAuction();

        updateDataDBAfterAuction.updateDataDBBeforeAuction(productId, clientsParticipating);
        deleteAuctionFromHouse(idAuction);

        ripOffBrokerAuction(brokers);

        paymentBroker(brokersAndClients);

        INotifierMail iNotifierMail = new NotifierMailAdapter();
        iNotifierMail.sendWinnerEmail(winner.getEmail(), productInfo);

        notifyParticipantsAuctionEnd(clientsParticipating);


    }

    private User mechanismAuction(Map<Broker, List<Pair<User, Double>>> brokersAndClients, List<User> clientsParticipating) {
        User winner = null;

        List<Double> finalCurrentBids = new ArrayList<>();

        List<Double> bids;
        for (int i = 0; i < noMaxSteps; i++) {
            bids = new ArrayList<>();
            List<Double> currentBids = bids;
            brokersAndClients.forEach((broker, usersAndMaxBid) -> {
                for (Pair<User, Double> userDoublePair : usersAndMaxBid) {
                    double bestBid = chooseStrategy(userDoublePair.getRight());
                    currentBids.add(bestBid);
                }
            });
            finalCurrentBids = currentBids;
            if(checkBids(currentBids)) maxBid = AuctionHouse.getInstance().calculateMaximumBid(currentBids);
            else {
                winner = declareWinnerLastRemaining(brokersAndClients);
                break;
            }
        }

        if(winner == null) {
            double maxim = Collections.max(finalCurrentBids);
            winner = clientsParticipating.get(finalCurrentBids.indexOf(maxim));
            winner.setWonAuctions(winner.getWonAuctions() + 1);
        }

        return winner;
    }

    private void notifyParticipantsAuctionEnd(List<User> clientsParticipating) {
        INotifierMail iNotifierMail = new NotifierMailAdapter();
        clientsParticipating.forEach(user -> {
            iNotifierMail.toParticipants(user.getEmail(), idAuction);
        });
    }

    private void paymentBroker(Map<Broker, List<Pair<User, Double>>> brokersAndClients) {
        brokersAndClients.forEach((broker, clientsAndBids) -> {
            clientsAndBids.forEach(client -> {
                broker.setAccumulatedSum(broker.getAccumulatedSum() +
                        broker.sumValueCalculator(client.getRight(), client.getLeft()));
            });
        });
    }

    private void ripOffBrokerAuction(Map<Integer, Broker> brokers) {
        brokers.forEach((integer, broker) -> broker.getAuctionAndUserAssigned().remove(idAuction));
    }

    private void deleteAuctionFromHouse(int idAuction) {

    }

    public int getNoMaxSteps() {
        return noMaxSteps;
    }

    private User declareWinnerLastRemaining(Map<Broker, List<Pair<User, Double>>> brokersAndClients) {
        List<Broker> keys = new ArrayList<>(brokersAndClients.keySet());
        Broker lastBroker = keys.get(keys.size() - 1);
        List<Pair<User, Double>> last = brokersAndClients.get(lastBroker);
        User lastClient = last.get(last.size() - 1).getLeft();

        if(last.get(last.size() - 1).getRight() > minBid)
            lastClient.setWonAuctions(lastClient.getWonAuctions() + 1);


        return lastClient;
    }

    private boolean checkBids(List<Double> finalCurrentBids) {
        for (Double finalCurrentBid : finalCurrentBids) {
            if(finalCurrentBid!=-1) return true;
        }
        return false;
    }

    private double chooseStrategy(double maxUserBid) {
        int randomStrategy = Main.random.nextInt(3) + 1;
        BidContext context;
        Strategy strategy;
        if (randomStrategy == 1) strategy = new CallDouble(maxBid, maxUserBid);
        else if (randomStrategy == 2) strategy = new CallHalfMore(maxBid, maxUserBid);
        else strategy = new CallMore(maxBid, maxUserBid);

        context = new BidContext(strategy);
        return context.executeStrategy();
    }

    private Pair<List<User>, Map<Broker, List<Pair<User, Double>>>> getBrokerAndClients(Map<Integer, Broker> brokers, List<User> userList) {
        Map<Broker, List<Pair<User, Double>>> brokersAndClients = new HashMap<>();

        List<User> participants = new ArrayList<>();

        brokers.forEach((integer, broker) -> {
            if (broker.getAuctionAndUserAssigned().containsKey(idAuction)) {
                Map<String, Double> usersAndBids = broker.getAuctionAndUserAssigned().get(idAuction);
                if (!brokersAndClients.containsKey(broker)) brokersAndClients.put(broker, new ArrayList<>());
                usersAndBids.forEach((username, bid) -> {
                    User user = userList.stream().filter(userIt -> userIt.getUsername().equals(username))
                            .collect(Collectors.toList()).get(0);
                    user.setNoParticipation(user.getNoParticipation() + 1);
                    participants.add(user);
                    brokersAndClients.get(broker).add(new ImmutablePair<>(user, bid));
                });
            }
        });
        return new ImmutablePair<>(participants, brokersAndClients);
    }
}
