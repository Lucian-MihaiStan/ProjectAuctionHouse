package auction;

import auction.updatedata.UpdateDataDBAfterAuction;
import auction_house.AuctionHouse;
import client.User;
import employee.Broker;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import products.Product;

import java.util.*;
import java.util.stream.Collectors;

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


    public synchronized void start(Map<Integer, Broker> brokers, List<User> userList) {
        // notify users auction started
        this.notifyHelper.notifyPAuctionStart(brokers, idAuction);

        Product productInfo = AuctionHouse.getInstance().getProductsList()
                .stream().filter(product -> product.getId() == productId).collect(Collectors.toList()).get(0);

        Pair<List<User>, Map<Broker, List<Pair<User, Double>>>> brokersAndClientsAssigned = getBrokerAndClients(brokers, userList);

        List<User> clientsParticipating = brokersAndClientsAssigned.getLeft();
        Map<Broker, List<Pair<User, Double>>> brokersAndClients = brokersAndClientsAssigned.getRight();

        maxCurrentBid = findFirstMaxBid(brokersAndClients);
        minBid = productInfo.getMinimumPrice();

        // Find the winner
        User winner = mechanismAuction(brokersAndClients, clientsParticipating);

        if(winner == null) {
            this.restoreAuction();
            this.notifyHelper.notifyPAuctionEnd(clientsParticipating, idAuction);
            ripOffBrokerAuction(brokers);
            return;
        }

        Broker broker = findWinnerBroker(brokersAndClients, winner);

        // Update data in database
        if(broker!=null) broker.deleteProduct(productId);

        UpdateDataDBAfterAuction updateDataDBAfterAuction = new UpdateDataDBAfterAuction();
        updateDataDBAfterAuction.updateDataDBBeforeAuction(productId, clientsParticipating);

        // Delete auction from auctionsList
        AuctionHouse.getInstance().deleteAuctionFromHouse(idAuction);

        // Delete brokers communications with users
        ripOffBrokerAuction(brokers);

        AuctionHouse.getInstance().payBrokers(brokersAndClients);

        this.notifyHelper.notifyWinner(winner, productInfo);
        this.notifyHelper.notifyPAuctionWasWon(clientsParticipating, idAuction);
    }

    private Broker findWinnerBroker(Map<Broker, List<Pair<User, Double>>> brokersClients, User winner) {
        Broker brokerWinner;
        for(Map.Entry<Broker, List<Pair<User, Double>>> entry : brokersClients.entrySet()) {
            List<Pair<User, Double>> usersAssigned = entry.getValue();
            for (Pair<User, Double> usersBids : usersAssigned) {
                if(usersBids.getLeft().getUsername().equals(winner.getUsername())) {
                    brokerWinner = entry.getKey();
                    return brokerWinner;
                }
            }
        }
        return null;
    }

    private double findFirstMaxBid(Map<Broker, List<Pair<User, Double>>> brokersAndClients) {
        List<Double> firstBids = new ArrayList<>();
        brokersAndClients.forEach((broker, usb) ->
                usb.forEach(ub -> firstBids.add(ub.getRight())));
        return AuctionHouse.getInstance().calculateMaximumBid(firstBids);
    }

    private void restoreAuction() {
        this.noCurrentParticipants = 0;
    }

    private User mechanismAuction(Map<Broker, List<Pair<User, Double>>> brokersAndClients, List<User> clientsParticipating) {
        User winner = null;

        List<Double> finalCurrentBids = new ArrayList<>();

        for (int i = 0; i < noMaxSteps; i++) {
            List<Double> currentBids = new ArrayList<>();
            brokersAndClients.forEach((broker, usersAndMaxBid) -> {
                for (Pair<User, Double> userAndBid : usersAndMaxBid) {
                    User user = userAndBid.getLeft();
                    double bid = user.askBid(maxCurrentBid, userAndBid.getRight());
                    currentBids.add(bid);
                }
            });
            finalCurrentBids = currentBids;
            if(checkBids(currentBids)) {
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

    private User declareWinnerLastRemaining(Map<Broker, List<Pair<User, Double>>> brokersAndClients) {
        List<Broker> keys = new ArrayList<>(brokersAndClients.keySet());
        Broker lastBroker = keys.get(keys.size() - 1);
        List<Pair<User, Double>> last = brokersAndClients.get(lastBroker);
        User lastClient = last.get(last.size() - 1).getLeft();

        if(last.get(last.size() - 1).getRight() > minBid)
            lastClient.setWonAuctions(lastClient.getWonAuctions() + 1);

        return lastClient;
    }

    private void ripOffBrokerAuction(Map<Integer, Broker> brokers) {
        brokers.forEach((integer, broker) -> broker.getAuctionAndUserAssigned().remove(idAuction));
    }

    public int getNoMaxSteps() {
        return noMaxSteps;
    }

    private boolean checkBids(List<Double> finalCurrentBids) {
        for (Double finalCurrentBid : finalCurrentBids) {
            if(finalCurrentBid!=-1) return true;
        }
        return false;
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
