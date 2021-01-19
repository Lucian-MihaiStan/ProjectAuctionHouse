package commander;

import auction_house.AuctionHouse;

import static java.lang.System.*;

public class ListProducts implements ICommand {
    @Override
    public void execute() {
        AuctionHouse.getInstance().getProductsList().forEach(out::println);
    }
}
