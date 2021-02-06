package employee.factory;

public class NewLPCom implements CommissionCalculator {
    @Override
    public double calculate(double bid) {
        return ((double) 25) / 100 * bid;
    }
}
