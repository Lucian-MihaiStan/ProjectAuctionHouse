package employee;

import auction.Auction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Broker implements IEmployee {
    private final int id;
    private int accumulatedSum;
    private final List<Pair<Auction, List<String>>> auctionsList = new ArrayList<>();

    public Broker(int id) {
        this.id = id;
        this.accumulatedSum = 0;
    }

    public void addAuction(Auction auction, String user) {
        if(this.auctionsList.stream().filter(auctionListPair ->
                auctionListPair.getLeft().getId() == auction.getId()).findFirst().isEmpty()) {
            List<String> userList = new ArrayList<>(auction.getUsernames());
            auctionsList.add(new ImmutablePair<>(auction, userList));
            return;
        }
        Pair<Auction, List<String>> auctionListPair = auctionsList
                .stream()
                .filter(auctionListPairIt -> auctionListPairIt.getLeft().getId() == auction.getId())
                .collect(Collectors.toList()).get(0);
        auctionListPair.getRight().add(user);
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
