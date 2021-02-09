package commander.auctions;

import auction.Auction;
import commander.ICommand;
import socketserver.ServerClientThread;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ShowAuctions implements ICommand, Runnable {
    private ServerClientThread serverClientThread;
    @Override
    public synchronized void execute(ServerClientThread sct) {
        Map<Integer, Auction> auctions = sct.getAuctionHouse().getAuctionsActive();
        ServerClientThread.Helper commandResult = ServerClientThread.Helper.getInstance();
        StringBuilder stringBuilder = new StringBuilder();


        Set<Integer> keySet = auctions.keySet();
        keySet.forEach(key -> {
            stringBuilder.append('|');
            Auction auction = auctions.get(key);
            stringBuilder
                    .append("===================Auction ").append(auction.getIdAuction()).append("===================|")
                    .append(sct.getAuctionHouse().getProductsList().stream().filter(product ->
                            product.getId() == auction.getProductId()).collect(Collectors.toList()).get(0))
                    .append("|")
                    .append(" noCurrentParticipants=")
                    .append(auction.getNoCurrentParticipants())
                    .append(" noMaxParticipants=")
                    .append(auction.getNoParticipants())
                    .append(" noMaxSteps=")
                    .append(auction.getNoMaxSteps())
                    .append('|');
        });

        commandResult.setCommandResult(commandResult.getCommandResult().append(stringBuilder));
    }

    @Override
    public void setSct(ServerClientThread sct) {
        this.serverClientThread = sct;
    }

    @Override
    public void run() {
        this.execute(this.serverClientThread);
    }
}
