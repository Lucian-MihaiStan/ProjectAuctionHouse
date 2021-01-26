package auction;

import socketserver.Main;
import socketserver.ServerClientThread;

import java.util.ArrayList;
import java.util.List;

public class Auction {
    private final int productId;
    private int noCurrentParticipants;
    private int noParticipants;
    private final int id;
    private final int noMaxSteps;
    private List<String> usernames;
    private List<Integer> bids;
    private List<Integer> maximumBids;

    public Auction(int productId, int noCurrentParticipants, int noParticipants, int id, int noMaxSteps,
                   List<String> usernames, List<Integer> bids) {
        this.id = id;
        this.productId = productId;
        this.noCurrentParticipants = noCurrentParticipants;
        this.noParticipants = noParticipants;
        this.noMaxSteps = noMaxSteps;
        this.usernames = usernames;
        this.bids = bids;
        this.maximumBids = new ArrayList<>();
    }

    public List<Integer> getMaximumBids() {
        return maximumBids;
    }

    public void setMaximumBids(List<Integer> maximumBids) {
        this.maximumBids = maximumBids;
    }

    public int getNoCurrentParticipants() {
        return noCurrentParticipants;
    }

    public void setNoCurrentParticipants(int noCurrentParticipants) {
        this.noCurrentParticipants = noCurrentParticipants;
    }

    public void setNoParticipants(int noParticipants) {
        this.noParticipants = noParticipants;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public List<Integer> getBids() {
        return bids;
    }

    public void setBids(List<Integer> bids) {
        this.bids = bids;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "productId=" + productId +
                ", noCurrentParticipants=" + noCurrentParticipants +
                ", noParticipants=" + noParticipants +
                ", id=" + id +
                ", noMaxSteps=" + noMaxSteps +
                ", usernames=" + usernames +
                ", bids=" + bids +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getNoParticipants() {
        return noParticipants;
    }

    public int getProductId() {
        return productId;
    }

    public int getNoMaxSteps() {
        return noMaxSteps;
    }

    public void notifyUsers(String username, int productId) {
//        List<ServerClientThread> sctList = Main.sctList;
//        for (ServerClientThread thread : sctList) {
//            if(!thread.getMySQLConnection().getUsername().equals(username)) {
//                String notify = "|" + "User " + username +
//                        " enroll to auction for product with id = " +
//                        productId +
//                        "|";
//
//                thread.setNotifier(notify);
//            }
//        }
    }
}
