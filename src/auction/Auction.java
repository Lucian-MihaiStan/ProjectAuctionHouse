package auction;

import auction.updatedata.UpdateDataDBAfterAuction;
import auction_house.AuctionHouse;
import client.User;
import employee.Broker;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import products.Product;
import socketserver.Main;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class Auction {
    private int productId;
    private int noCurrentParticipants;
    private int noParticipants;
    private int idAuction;
    private int noMaxSteps;

    private double maxCurrentBid;
    private double minBid;

    private final NotifyHelper notifyHelper = new NotifyHelper();

    public int getNoCurrentParticipants() {
        return noCurrentParticipants;
    }

    public void setNoCurrentParticipants(int noCurrentParticipants) { this.noCurrentParticipants = noCurrentParticipants; }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getMaxCurrentBid() {
        return maxCurrentBid;
    }

    public void setMaxCurrentBid(double maxCurrentBid) {
        this.maxCurrentBid = maxCurrentBid;
    }

    public double getMinBid() {
        return minBid;
    }

    public void setMinBid(double minBid) {
        this.minBid = minBid;
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


    public synchronized void start(Map<Integer, Broker> brokers, List<User> userList) {
        // notify users auction started
        this.notifyHelper.notifyPAuctionStart(brokers, idAuction);

        Product productInfo = getProductInfo();

        Pair<List<User>, Map<Broker, List<Triple<User, Double, Double>>>> brokersAndClientsAssigned = getBrokersAndClients(brokers, userList);

        List<User> clientsParticipating = brokersAndClientsAssigned.getLeft();
        Map<Broker, List<Triple<User, Double, Double>>> brokersAndClients = brokersAndClientsAssigned.getRight();

        maxCurrentBid = findFirstMaxBid(brokersAndClients);
        minBid = productInfo.getMinimumPrice();

        // Find the winner
        User winner = mechanismAuction(brokersAndClients, clientsParticipating);

        if(winner == null) {
            this.restoreAuction();
            this.notifyHelper.notifyPAuctionEnd(clientsParticipating, idAuction);
            AuctionHouse.getInstance().payBrokers(brokersAndClients);
            AuctionHouse.getInstance().ripOffBrokerAuction(brokers, idAuction);
            return;
        }

        Broker broker = findWinnerBroker(brokersAndClients, winner);

        // Update data in database
        if(broker!=null) broker.deleteProduct(productId);

        UpdateDataDBAfterAuction updateDataDBAfterAuction = new UpdateDataDBAfterAuction();
        updateDataDBAfterAuction.updateDataDBAfterAuction(productId, clientsParticipating);

        // Delete auction from auctionsList
        AuctionHouse.getInstance().deleteAuctionFromHouse(idAuction);

        // Delete brokers communications with users
        AuctionHouse.getInstance().ripOffBrokerAuction(brokers, idAuction);

        AuctionHouse.getInstance().payBrokers(brokersAndClients);

        this.notifyHelper.notifyWinner(winner, productInfo);
        this.notifyHelper.notifyPAuctionWasWon(clientsParticipating, idAuction);
    }

    public Product getProductInfo() {
        return AuctionHouse.getInstance().getProductsList()
                .stream().filter(product -> product.getId() == productId).collect(Collectors.toList()).get(0);
    }

    private Broker findWinnerBroker(Map<Broker, List<Triple<User, Double, Double>>> brokersClients, User winner) {
        Broker brokerWinner;
        for(Map.Entry<Broker, List<Triple<User, Double, Double>>> entry : brokersClients.entrySet()) {
            List<Triple<User, Double, Double>> usersAssigned = entry.getValue();
            for (Triple<User, Double, Double> usersBids : usersAssigned) {
                if(usersBids.getLeft().getUsername().equals(winner.getUsername())) {
                    brokerWinner = entry.getKey();
                    return brokerWinner;
                }
            }
        }
        return null;
    }

    private double findFirstMaxBid(Map<Broker, List<Triple<User, Double, Double>>> brokersAndClients) {
        List<Double> firstBids = new ArrayList<>();
        brokersAndClients.forEach((broker, usb) ->
                usb.forEach(ub -> firstBids.add(ub.getMiddle())));
        return AuctionHouse.getInstance().calculateMaximumBid(firstBids);
    }

    private void restoreAuction() {
        this.noCurrentParticipants = 0;
    }

    public User mechanismAuction(Map<Broker, List<Triple<User, Double, Double>>> brokersAndClients, List<User> clientsParticipating) {
        User winner = null;

        List<Double> finalCurrentBids = new ArrayList<>();

        for (int i = 0; i < noMaxSteps; i++) {
            List<Double> currentBids = new ArrayList<>();
            brokersAndClients.forEach((broker, usersAndMaxBid) -> {
                for (Triple<User, Double, Double> userAndBid : usersAndMaxBid) {
                    User user = userAndBid.getLeft();
                    double bid = broker.askClientBid(user, maxCurrentBid, userAndBid.getRight());
                    currentBids.add(bid);
                }
            });
            finalCurrentBids = currentBids;
            if(AuctionHouse.getInstance().checkBids(currentBids)) {
                maxCurrentBid = AuctionHouse.getInstance().calculateMaximumBid(currentBids);
                clientsParticipating.forEach(user -> user.getAuctionAndMaxBid().replace(idAuction, maxCurrentBid));
            }
            else {
                winner = declareWinnerLastRemaining(brokersAndClients);
                break;
            }
        }
        if(winner == null) {
            double maxim = Collections.max(finalCurrentBids);
            if(maxim < minBid) return null;
            winner = clientsParticipating.get(finalCurrentBids.indexOf(maxim));
            winner.setWonAuctions(winner.getWonAuctions() + 1);
        }

        return winner;
    }

    private User declareWinnerLastRemaining(Map<Broker, List<Triple<User, Double, Double>>> brokersAndClients) {
        List<Broker> keys = new ArrayList<>(brokersAndClients.keySet());
        Broker lastBroker = keys.get(keys.size() - 1);
        List<Triple<User, Double, Double>> last = brokersAndClients.get(lastBroker);
        User lastClient = last.get(last.size() - 1).getLeft();

        if(last.get(last.size() - 1).getRight() > minBid){
            lastClient.setWonAuctions(lastClient.getWonAuctions() + 1);
            return lastClient;

        }
        return null;
    }

    public int getNoMaxSteps() {
        return noMaxSteps;
    }

    private Pair<List<User>, Map<Broker, List<Triple<User, Double, Double>>>> getBrokersAndClients(Map<Integer, Broker> brokers, List<User> userList) {
        Map<Broker, List<Triple<User, Double, Double>>> brokersAndClients = new HashMap<>();

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
                    brokersAndClients.get(broker).add(new ImmutableTriple<>(user, Main.random.nextDouble() * 100, bid));
                });
            }
        });
        return new ImmutablePair<>(participants, brokersAndClients);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return productId == auction.productId &&
                noCurrentParticipants == auction.noCurrentParticipants &&
                noParticipants == auction.noParticipants &&
                idAuction == auction.idAuction &&
                noMaxSteps == auction.noMaxSteps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, noCurrentParticipants, noParticipants, idAuction, noMaxSteps, maxCurrentBid, minBid, notifyHelper);
    }
}
