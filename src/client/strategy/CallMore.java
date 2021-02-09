package client.strategy;

public class CallMore implements Strategy {
    private final double maxBid;
    private final double maxUserBid;

    public CallMore(double maxBid, double maxUserBid) {
        this.maxBid = maxBid;
        this.maxUserBid = maxUserBid;
    }

    @Override
    public double bidCalculator() {
        if(maxBid + 200 <= maxUserBid) return maxBid + 200;
        return -1;
    }
}
