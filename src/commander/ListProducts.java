package commander;

import products.Product;
import socketserver.ServerClientThread;

public class ListProducts implements ICommand {
    @Override
    public void execute() {
        ServerClientThread.Helper helper = ServerClientThread.Helper.getInstance();
        StringBuilder productSB = new StringBuilder();
        int id = 0;
        for (Product product : ServerClientThread.auctionHouse.getProductsList()) {
            id++;
            productSB.append(id);
            productSB.append(") ");
            productSB.append('\n');
            productSB.append(product.toString());
        }
        helper.setCommandResult(helper.getCommandResult().append(productSB));
    }
}
