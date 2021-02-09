package client.strategy;

/**
 * call the current bid plus 200
 */
public class CallMore implements Strategy {
    private final double maxBid;
    private final double maxUserBid;

    /**
     * constructor for the strategy
     * @param maxBid max current bid
     * @param maxUserBid max bid of user
     */
    public CallMore(double maxBid, double maxUserBid) {
        this.maxBid = maxBid;
        this.maxUserBid = maxUserBid;
    }

    /**
     * calculate the new bid
     * @return bid calculated by each type of strategy
     */
    @Override
    public double bidCalculator() {
        if(maxBid + 200 <= maxUserBid) return maxBid + 200;
        return -1;
    }
}
