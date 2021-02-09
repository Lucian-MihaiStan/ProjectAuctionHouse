package client.strategy;

/**
 * strategy to call two times more money for the next bid step
 */
public class CallDouble implements Strategy {
    private final double maxBid;
    private final double maxUserBid;

    /**
     * constructor for the strategy
     * @param maxBid max current bid
     * @param maxUserBid max bid of user
     */
    public CallDouble(double maxBid, double maxUserBid) {
        this.maxBid = maxBid;
        this.maxUserBid = maxUserBid;
    }

    /**
     * calculate the new bid
     * @return new bid
     */
    @Override
    public double bidCalculator() {
        if(2 * maxBid <= maxUserBid) return 2 * maxBid;
        return -1;
    }
}
