package employee.factory;

public class OldLPCom implements CommissionCalculator {
    @Override
    public double calculate(double bid) {
        return ((double) 10) / 100 * bid;
    }
}
