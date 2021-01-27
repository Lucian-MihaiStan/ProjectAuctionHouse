package commander.auctions;

import auction.Auction;
import commander.ICommand;
import socketserver.ServerClientThread;

import java.util.List;
import java.util.stream.Collectors;

public class ShowAuctions implements ICommand {
    @Override
    public void execute(ServerClientThread sct) {
        synchronized (sct.getMySQLConnection()) {
            List<Auction> auctions = sct.getAuctionHouse().getAuctionsActive();
            ServerClientThread.Helper commandResult = ServerClientThread.Helper.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('|');
            auctions.forEach(auction -> stringBuilder
                    .append("===================Auction ").append(auction.getIdAuction()).append("===================|")
                    .append(sct.getAuctionHouse().getProductsList().stream().filter(product ->
                            product.getId() == auction.getProductId()).collect(Collectors.toList()).get(0))
                    .append("|")
                    .append(" noCurrentParticipants=")
                    .append(auction.getNoCurrentParticipants())
                    .append(" noMaxParticipants=")
                    .append(auction.getNoParticipants())
                    .append('|')
            );
            commandResult.setCommandResult(commandResult.getCommandResult().append(stringBuilder));
        }
    }
}
