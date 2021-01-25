package commander;

import auction_house.AuctionHouse;
import socketserver.ServerClientThread;

import java.util.List;
import java.util.Map;

public interface ICommand {
    void execute(ServerClientThread serverClientThread);
}
