package commander;

import auction.Auction;
import socketserver.ServerClientThread;


public class EnrollToAuction implements ICommand{
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
            Auction auction = sct.getAuctionHouse().getAuctionsActive().stream().filter(auctionIt -> auctionIt.getId() == idAuction)
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
            auction.setNoCurrentParticipants(auction.getNoCurrentParticipants() + 1);
            if (auction.getNoParticipants() == auction.getNoCurrentParticipants())
                sct.getAuctionHouse().notifyBrokers(auction);
            auction.getUsernames().add(sct.getMySQLConnection().getUsername());
            auction.getBids().add(-1);
            auction.getMaximumBids().add(maximumPrice);
            helper.setCommandResult(helper.getCommandResult().append("You have been added to auction"));
            auction.notifyUsers(sct.getMySQLConnection().getUsername(), auction.getProductId());
        }
    }

    private synchronized boolean containsUser(Auction auction) {
        return auction.getUsernames().stream().anyMatch(sct.getMySQLConnection().getUsername()::equals);
    }
}
