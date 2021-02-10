package employee.factory;

/**
 * calculate commission for a old legal person
 */
public class OldLPCom implements CommissionCalculator {
    /**
     * calculator
     * @param bid bid bet at the start of the auction
     * @return the commission
     */
    @Override
    public double calculate(double bid) {
        return ((double) 10) / 100 * bid;
    }
}
