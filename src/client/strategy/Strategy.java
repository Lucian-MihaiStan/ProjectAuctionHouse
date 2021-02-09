package client.strategy;

/**
 * interface for strategy of user
 */
public interface Strategy {
    /**
     * calculate the new bid
     * @return bid calculated by each type of strategy
     */
    double bidCalculator();
}
