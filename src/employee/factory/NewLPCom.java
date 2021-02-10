package employee.factory;

/**
 * calculate commission for a new legal person
 */
public class NewLPCom implements CommissionCalculator {
    /**
     * calculator
     * @param bid bid bet at the start of the auction
     * @return the commission
     */
    @Override
    public double calculate(double bid) {
        return ((double) 25) / 100 * bid;
    }
}
