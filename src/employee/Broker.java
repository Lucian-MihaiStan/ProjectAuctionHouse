package employee;

import auction_house.AuctionHouse;
import client.User;
import client.individualperson.IndividualPerson;
import employee.factory.*;

import java.util.HashMap;
import java.util.Map;

public class Broker implements IEmployee {
    private final int id;
    private double accumulatedSum;

    private Map<Integer, Map<String, Double>> auctionAndUserAssigned = new HashMap<>();

    public Broker(int id) {
        this.id = id;
        this.accumulatedSum = 0;
    }

    public Map<Integer, Map<String, Double>> getAuctionAndUserAssigned() {
        return auctionAndUserAssigned;
    }

    public void setAuctionAndUserAssigned(Map<Integer, Map<String, Double>> auctionAndUserAssigned) {
        this.auctionAndUserAssigned = auctionAndUserAssigned;
    }

    @Override
    public void deleteProduct(int id) {
        synchronized (AuctionHouse.getInstance().getProductsList()) {
            AuctionHouse.getInstance().getProductsList().removeIf(product -> product.getId() == id);
        }
    }

    @Override
    public String toString() {
        return "Broker{" +
                "id=" + id +
                ", accumulatedSum=" + accumulatedSum +
                ", auctionAndUserAssigned=" + auctionAndUserAssigned +
                '}';
    }

    public int getId() {
        return id;
    }

    public double getAccumulatedSum() {
        return accumulatedSum;
    }

    public void setAccumulatedSum(double accumulatedSum) {
        this.accumulatedSum = accumulatedSum;
    }

    public double sumValueCalculator(Double bid, User user) {
        CommissionCalculator cc;
        if(user instanceof IndividualPerson) {
            if(user.getNoParticipation() < 5) cc = new NewIPCom();
            else cc = new OldIPCom();
        }
        else {
            if(user.getNoParticipation() < 25) cc = new NewLPCom();
            else cc = new OldLPCom();
        }
        return cc.calculate(bid);
    }
}
