package auction;

import auction.notifieradapter.INotifierMail;
import auction.notifieradapter.NotifierMailAdapter;
import auction_house.AuctionHouse;
import client.User;
import products.Product;

import java.util.List;
import java.util.stream.Collectors;

public class Auction {
    private int productId;
    private int noCurrentParticipants;
    private int noParticipants;
    private int idAuction;
    private int noMaxSteps;
    private List<String> usernames;
    private List<Integer> bids;
    private List<Integer> maximumBids;

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
                ", idAuction=" + idAuction +
                ", noMaxSteps=" + noMaxSteps +
                ", usernames=" + usernames +
                ", bids=" + bids +
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

    public int getNoMaxSteps() {
        return noMaxSteps;
    }

    public void notifyUsers(List<String> usernames, int productId) {
        Product productInfo = AuctionHouse.getInstance()
                .getProductsList()
                .stream()
                .filter(product -> product.getId() == productId)
                .collect(Collectors.toList()).get(0);
        String notify = "Auction for product " + productInfo.toString() + " has started please join at the table";
        usernames.forEach(
                username -> {
                    User user = AuctionHouse.getInstance().getUserList().stream().filter(
                            userIt -> username.equals(userIt.getUsername())
                    ).collect(Collectors.toList()).get(0);
                    String email = user.getEmail();
                    INotifierMail iNotifierMail = new NotifierMailAdapter();
//                    iNotifierMail.sendEmail(email, notify);
                }
        );
    }
}
