package commander;

import auction.Auction;
import socketserver.ServerClientThread;

import java.util.List;

public class ShowAuctions implements ICommand {
    @Override
    public void execute(ServerClientThread sct) {
        List<Auction> auctions = sct.getAuctionHouse().getAuctionsActive();
        ServerClientThread.Helper commandResult = ServerClientThread.Helper.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('|');
        auctions.forEach(auction -> stringBuilder
                .append("Auction (id=")
                .append(auction.getId())
                .append(" productId=")
                .append(auction.getProductId())
                .append(" noCurrentParticipants=")
                .append(auction.getNoCurrentParticipants())
                .append(" noMaxParticipants=")
                .append(auction.getNoParticipants())
                .append(") ")
                .append('|')
        );
        commandResult.setCommandResult(commandResult.getCommandResult().append(stringBuilder));
    }
}
