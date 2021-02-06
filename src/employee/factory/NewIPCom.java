package employee.factory;

public class NewIPCom implements CommissionCalculator {

    @Override
    public double calculate(double bid) {
        return ((double) 20) / 100 * bid;
    }
}
