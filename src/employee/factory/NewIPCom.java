package employee.factory;

/**
 * calculate commission for a new individual person
 */
public class NewIPCom implements CommissionCalculator {

    /**
     * calculator
     * @param bid bid bet at the start of the auction
     * @return the commission
     */
    @Override
    public double calculate(double bid) {
        return ((double) 20) / 100 * bid;
    }
}
