package strategy;

public class CallDouble implements Strategy {
    private final double maxBid;
    private final double maxUserBid;

    public CallDouble(double maxBid, double maxUserBid) {
        this.maxBid = maxBid;
        this.maxUserBid = maxUserBid;
    }

    @Override
    public double bidCalculator() {
        if(2 * maxBid <= maxUserBid) return 2 * maxBid;
        return -1;
    }
}
