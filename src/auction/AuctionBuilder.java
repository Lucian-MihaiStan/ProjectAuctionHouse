package auction;

import java.util.List;

public class AuctionBuilder {
    private Auction auction = new Auction();

    public AuctionBuilder withId(int auctionId) {
        auction.setIdAuction(auctionId);
        return this;
    }

    public AuctionBuilder withNoCurrentParticipants(int participants) {
        auction.setNoCurrentParticipants(participants);
        return this;
    }

    public AuctionBuilder withNoParticipants(int noParticipants) {
        auction.setNoParticipants(noParticipants);
        return this;
    }

    public AuctionBuilder withProductId(int productId) {
        auction.setProductId(productId);
        return this;
    }

    public AuctionBuilder withNoMaxSteps(int noMaxSteps) {
        auction.setNoMaxSteps(noMaxSteps);
        return this;
    }

    public AuctionBuilder withUserNames(List<String> userNames) {
        auction.setUsernames(userNames);
        return this;
    }

    public AuctionBuilder withBids(List<Integer> bidsList) {
        auction.setBids(bidsList);
        return this;
    }

    public AuctionBuilder withMaximumBids(List<Integer> maximumBids) {
        auction.setMaximumBids(maximumBids);
        return this;
    }

    public Auction build() {
        return auction;
    }
}
