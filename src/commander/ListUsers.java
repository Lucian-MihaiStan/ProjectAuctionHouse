package commander;

import auction_house.AuctionHouse;

import static java.lang.System.*;

public class ListUsers implements ICommand {
    @Override
    public void execute() {
        AuctionHouse.getInstance().getUserList().forEach(
                out::println
        );

    }
}
