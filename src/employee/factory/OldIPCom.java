package employee.factory;

public class OldIPCom implements CommissionCalculator {
    @Override
    public double calculate(double bid) {
        return ((double) 15) / 100 * bid;
    }
}
