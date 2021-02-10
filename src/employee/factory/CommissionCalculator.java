package employee.factory;

/**
 * factory commission calculator interface
 */
public interface CommissionCalculator {
    /**
     * calculator
     * @param bid bid bet at the start of the auction
     * @return the commission
     */
    double calculate(double bid);
}
