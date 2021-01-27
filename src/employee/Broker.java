package employee;

import auction.Auction;
import client.User;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broker implements IEmployee {
    private final int id;
    private int accumulatedSum;
    private final List<Auction> auctionsList = new ArrayList<>();
    private final List<String> clientsUsernames = new ArrayList<>();

    private Map<Integer, List<Map<Integer, Integer>>> auctionAndUserAssigned = new HashMap<>();

    public Broker(int id) {
        this.id = id;
        this.accumulatedSum = 0;
    }

    public List<Auction> getAuctionsList() {
        return auctionsList;
    }

    public List<String> getClientsUsernames() {
        return clientsUsernames;
    }

    public void communicateBidToAuctionHouse() {
//        TODO communicate through this method
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
                ", auctionsList=" + auctionsList +
                ", clientsUsernames=" + clientsUsernames +
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
