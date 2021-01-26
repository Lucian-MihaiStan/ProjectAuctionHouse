package commander.bid;

import auction.Auction;
import auction_house.AuctionHouse;
import client.User;
import commander.ICommand;
import employee.Broker;
import socketserver.ServerClientThread;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class Bid implements ICommand {
    private final int idProduct;
    private final int amount;

    private ServerClientThread sct;

    public Bid(int idProduct, int amount) {
        this.idProduct = idProduct;
        this.amount = amount;
    }

    @Override
    public void execute(ServerClientThread sct) {
        this.sct = sct;
        AuctionHouse auctionHouse = sct.getAuctionHouse();
        String username = sct.getMySQLConnection().getUsername();
        List<Auction> auctionsActive =  auctionHouse.getAuctionsActive();
        List<Auction> auctions = auctionsActive
                .stream()
                .filter(auction -> auction.getProductId() == idProduct)
                .collect(Collectors.toList());
        if(auctions.isEmpty()) addNewAuction(username);
        else addBid(auctions.get(0), username);
    }

    private void addBid(Auction auction, String username) {

    }

    private void addUserToAuction(Auction auction, String username) {

    }

    private void addNewAuction(String username) {
//        find a random broker for client
        Random random = new Random();
        int brokerId = random.nextInt(3 - 1) + 1;

        Broker brokerClient = sct.getAuctionHouse().getBrokersList()
                .stream()
                .filter(broker -> broker.getId() == brokerId)
                .collect(Collectors.toList()).get(0);

//        set the client to the broker

        List<Auction> currentAuctionList;
        currentAuctionList = sct.getAuctionHouse().getAuctionsActive();

        int maxId = -1;
        for (Auction auction : sct.getAuctionHouse().getAuctionsActive()) {
            if(auction.getId() > maxId) maxId = auction.getId();
        }

//        Auction auction = new Auction(idProduct, 1, maxId + 1, random.nextInt(15 - 5) + 1,
//                Arrays.asList(username), Arrays.asList(amount));
//
//        currentAuctionList.add(auction);
//        sct.auctionHouse.setAuctionsActive(currentAuctionList);
    }
}
