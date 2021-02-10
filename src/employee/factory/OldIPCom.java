package employee.factory;

/**
 * calculate commission for a old individual person
 */
public class OldIPCom implements CommissionCalculator {
    /**
     * calculator
     * @param bid bid bet at the start of the auction
     * @return the commission
     */
    @Override
    public double calculate(double bid) {
        return ((double) 15) / 100 * bid;
    }
}
