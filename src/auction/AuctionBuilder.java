package auction;

/**
 * Builder class for an auction
 */
public class AuctionBuilder {
    private final Auction auction = new Auction();

    /**
     * build an auction with id
     * @param auctionId id of the auction
     * @return auction builder instance
     */
    public AuctionBuilder withId(int auctionId) {
        auction.setIdAuction(auctionId);
        return this;
    }

    /**
     * build an auction with current number of participants
     * @param participants number of participants of the auction
     * @return auction builder instance
     */
    public AuctionBuilder withNoCurrentParticipants(int participants) {
        auction.setNoCurrentParticipants(participants);
        return this;
    }

    /**
     * build an auction with the maximum number of participants
     * @param noParticipants maximum number of participants to this auction
     * @return auction builder instance
     */
    public AuctionBuilder withNoParticipants(int noParticipants) {
        auction.setNoParticipants(noParticipants);
        return this;
    }

    /**
     * build an auction with product id
     * @param productId product id
     * @return auction builder instance
     */
    public AuctionBuilder withProductId(int productId) {
        auction.setProductId(productId);
        return this;
    }

    /**
     * build an auction with the maximum number of steps
     * @param noMaxSteps maximum number of steps
     * @return auction builder instance
     */
    public AuctionBuilder withNoMaxSteps(int noMaxSteps) {
        auction.setNoMaxSteps(noMaxSteps);
        return this;
    }

    /**
     * assemble the auction
     * @return auction build
     */
    public Auction build() {
        return auction;
    }
}
