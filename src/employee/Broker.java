package employee;

import client.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broker implements IEmployee {
    private final int id;
    private int accumulatedSum;

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

    public int getAccumulatedSum() {
        return accumulatedSum;
    }

    public void setAccumulatedSum(int accumulatedSum) {
        this.accumulatedSum = accumulatedSum;
    }
}
