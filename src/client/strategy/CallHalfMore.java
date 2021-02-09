package client.strategy;

public class CallHalfMore implements Strategy{
    private final double maxBid;
    private final double maxUserBid;

    public CallHalfMore(double maxBid, double maxUserBid) {
        this.maxBid = maxBid;
        this.maxUserBid = maxUserBid;
    }

    @Override
    public double bidCalculator() {
        if(maxBid + maxBid / 2 <= maxUserBid) return maxBid + maxBid / 2;
        return -1;
    }
}
