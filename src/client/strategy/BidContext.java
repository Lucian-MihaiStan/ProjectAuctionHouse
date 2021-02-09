package client.strategy;

/**
 * bid context strategy
 */
public class BidContext {
    private final Strategy strategy;

    /**
     * builder
     * @param strategy strategy chosen by user
     */
    public BidContext(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * execute the strategy chosen by user
     * @return bid calculated
     */
    public double executeStrategy() {
        return strategy.bidCalculator();
    }
}
