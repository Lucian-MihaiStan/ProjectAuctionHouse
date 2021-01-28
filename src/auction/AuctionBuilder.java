package auction;


public class AuctionBuilder {
    private final Auction auction = new Auction();

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

    public Auction build() {
        return auction;
    }
}
