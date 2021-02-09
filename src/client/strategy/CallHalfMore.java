package client.strategy;

/**
 * call the current bid plus half more
 */
public class CallHalfMore implements Strategy{
    private final double maxBid;
    private final double maxUserBid;

    /**
     * constructor for the strategy
     * @param maxBid max current bid
     * @param maxUserBid max bid of user
     */
    public CallHalfMore(double maxBid, double maxUserBid) {
        this.maxBid = maxBid;
        this.maxUserBid = maxUserBid;
    }

    /**
     * calculate the new bid
     * @return bid calculated by each type of strategy
     */
    @Override
    public double bidCalculator() {
        if(maxBid + maxBid / 2 <= maxUserBid) return maxBid + maxBid / 2;
        return -1;
    }
}
