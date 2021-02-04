package employee;

import client.User;
import client.individualperson.IndividualPerson;

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
//        TODO delete product using this method
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
        if(user instanceof IndividualPerson) {
            if(user.getNoParticipation() < 5) return ((double) 20) / 100 * bid;
            else return ((double) 15) / 100 * bid;
        }
        else {
            if(user.getNoParticipation() < 25) return ((double) 25) / 100 * bid;
            else return ((double) 10) / 100 * bid;
        }
    }
}
