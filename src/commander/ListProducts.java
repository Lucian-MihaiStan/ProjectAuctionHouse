package commander;

import socketserver.ServerClientThread;

import static java.lang.System.*;

public class ListProducts implements ICommand {
    @Override
    public void execute() {
        ServerClientThread.Helper helper = ServerClientThread.Helper.getInstance();
        StringBuilder productSB = new StringBuilder();
        ServerClientThread.auctionHouse.getProductsList().forEach(
                product -> productSB.append(product.toString())
        );
        helper.setCommandResult(helper.getCommandResult().append(productSB));
    }
}
