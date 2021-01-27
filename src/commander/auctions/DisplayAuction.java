package commander.auctions;

import auction.Auction;
import commander.ICommand;
import products.Product;
import socketserver.ServerClientThread;

import java.util.List;
import java.util.stream.Collectors;

public class DisplayAuction implements ICommand {
    private final int auctionId;
    private ServerClientThread sct;

    public DisplayAuction(int auctionId) {
        this.auctionId = auctionId;
    }

    @Override
    public void execute(ServerClientThread sct) {
        synchronized (sct.getMySQLConnection()) {
            this.sct = sct;
            Auction auctionSearched = sct.getAuctionHouse().getAuctionsActive()
                    .stream().filter(auction -> auction.getIdAuction() == auctionId).collect(Collectors.toList()).get(0);
            String auctionInfo = buildInfo(auctionSearched);
            ServerClientThread.Helper commandResult = ServerClientThread.Helper.getInstance();
            commandResult.setCommandResult(commandResult.getCommandResult().append(auctionInfo));
        }
    }

    private String buildInfo(Auction auctionSearched) {
        StringBuilder info = new StringBuilder("|");
        Product productInfo = sct.getAuctionHouse().getProductsList()
                .stream().filter(product -> product.getId() == auctionSearched.getProductId())
                .collect(Collectors.toList()).get(0);

        info.append(productInfo.toString()).append("|");
        List<String> usersSubmitted = auctionSearched.getUsernames();
        List<Integer> bidsBet = auctionSearched.getBids();
        List<Integer> maximumBids = auctionSearched.getMaximumBids();

        if(auctionSearched.getNoParticipants() == auctionSearched.getNoCurrentParticipants()){
            info.append("|==Auction is in progress==|");
            info.append("Participants and Bids:|");
            for (int i = 0; i < usersSubmitted.size(); i++) {
                info
                        .append("User ").append(usersSubmitted.get(i))
                        .append(" with bid ").append(bidsBet.get(i))
                        .append(" with maximum bid ").append(maximumBids.get(i))
                        .append("|");
            }
        }
        else {
            info.append("|==Auction waits for users to join==|");
            if(auctionSearched.getNoCurrentParticipants() == 0) info.append("There are no participants joined |");
            else {
                info.append("Participants waiting:|");
                for (int i = 0; i < usersSubmitted.size(); i++) {
                    info
                            .append("User ").append(usersSubmitted.get(i))
                            .append(" with maximum bid ").append(maximumBids.get(i))
                            .append("|");
                }
            }

        }

        return info.toString();
    }
}
