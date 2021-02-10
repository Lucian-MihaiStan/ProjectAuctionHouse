package employee;

import auction_house.AuctionHouse;
import client.User;
import client.individualperson.IndividualPerson;
import employee.factory.*;

import java.util.HashMap;
import java.util.Map;

/**
 * broker employee
 */
public class Broker implements IEmployee {
    private final int id;
    private double accumulatedSum;

    /**
     * map from auction id to username to bid
     * @return
     */
    private Map<Integer, Map<String, Double>> auctionAndUserAssigned = new HashMap<>();

    /**
     * constructor for broker
     * @param id id of broker
     */
    public Broker(int id) {
        this.id = id;
        this.accumulatedSum = 0;
    }

    /**
     * sum commission calculator
     * @param bid the bid placed at the start of the auction
     * @param user user that placed the bid
     * @return commission depending on the type of user and seniority
     */
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

    public double askClientBid(User user, double maxCurrentBid, Double right) {
        return user.askBid(maxCurrentBid, right);
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

    public Map<Integer, Map<String, Double>> getAuctionAndUserAssigned() {
        return auctionAndUserAssigned;
    }

    public void setAuctionAndUserAssigned(Map<Integer, Map<String, Double>> auctionAndUserAssigned) {
        this.auctionAndUserAssigned = auctionAndUserAssigned;
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
}
