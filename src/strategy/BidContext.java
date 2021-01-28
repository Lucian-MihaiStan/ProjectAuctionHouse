package strategy;

public class BidContext {
    private final Strategy strategy;

    public BidContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public double executeStrategy() {
        return strategy.bidCalculator();
    }
}
