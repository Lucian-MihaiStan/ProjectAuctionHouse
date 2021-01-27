package commander.bid;

import auction_house.AuctionHouse;
import commander.ICommand;
import socketserver.ServerClientThread;

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
    }

}
