package commander.auctions;

import auction.Auction;
import commander.ICommand;
import socketserver.ServerClientThread;

import java.util.stream.Collectors;


public class EnrollToAuction implements ICommand {
    private final int idAuction;
    private final int maximumPrice;

    private ServerClientThread sct;

    public EnrollToAuction(int idAuction, int maximumPrice) {
        this.idAuction = idAuction;
        this.maximumPrice = maximumPrice;
    }

    @Override
    public void execute(ServerClientThread sct) {
        synchronized (sct.getMySQLConnection()) {
            this.sct = sct;
            Auction auction = sct.getAuctionHouse().getAuctionsActive().stream().filter(auctionIt -> auctionIt.getIdAuction() == idAuction)
                    .findAny().orElse(null);
            ServerClientThread.Helper helper = ServerClientThread.Helper.getInstance();
            if (auction == null) {
                helper.setCommandResult(helper.getCommandResult().append("Auction Id is wrong"));
                return;
            }
            if (containsUser(auction)) {
                helper.setCommandResult(helper.getCommandResult().append("You were already registered to this auction"));
                return;
            }
            if (auction.getNoCurrentParticipants() >= auction.getNoParticipants()) {
                helper.setCommandResult(helper.getCommandResult().append("There are already maximum number of participants"));
                return;
            }

            int idProduct = auction.getProductId();
            double minimumPrice = sct.getAuctionHouse().getProductsList()
                    .stream().filter(product -> product.getId() == idProduct).collect(Collectors.toList()).get(0)
                    .getMinimumPrice();

            if(minimumPrice > maximumPrice) {
                helper.setCommandResult(helper.getCommandResult()
                        .append("You have not been added to this auction because your maximum price " +
                                "is smaller then the minimum price of product"));
                return;
            }

            auction.setNoCurrentParticipants(auction.getNoCurrentParticipants() + 1);
            auction.getUsernames().add(sct.getMySQLConnection().getUsername());
            auction.getBids().add(-1);
            auction.getMaximumBids().add(maximumPrice);
            helper.setCommandResult(helper.getCommandResult().append("You have been added to auction"));
            if (auction.getNoParticipants() == auction.getNoCurrentParticipants()) {
                auction.notifyUsers(auction.getUsernames(), auction.getProductId());
                sct.getAuctionHouse().notifyBrokers(auction);
            }
        }
    }

    private synchronized boolean containsUser(Auction auction) {
        for (String username : auction.getUsernames()) {
            if (username.equals(sct.getMySQLConnection().getUsername())) return true;
        }
        return false;
    }
}
