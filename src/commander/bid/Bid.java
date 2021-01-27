package commander.bid;

import auction_house.AuctionHouse;
import commander.ICommand;
import socketserver.ServerClientThread;

public class Bid implements ICommand {
    private final int idProduct;

    private ServerClientThread sct;

    public Bid(int idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public void execute(ServerClientThread sct) {
        synchronized (sct.getMySQLConnection()) {
            this.sct = sct;
            AuctionHouse auctionHouse = sct.getAuctionHouse();
            String username = sct.getMySQLConnection().getUsername();

        }
    }

}
