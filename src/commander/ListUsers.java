package commander;

import auctionHouse.AuctionHouse;

import static java.lang.System.*;

public class ListUsers implements ICommand {
    @Override
    public void execute() {
        AuctionHouse.getInstance().getClientsList().forEach(
                out::println
        );

    }
}
