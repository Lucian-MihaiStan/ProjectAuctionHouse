package commander;

import products.Product;
import socketserver.ServerClientThread;

public class ListProducts implements ICommand, Runnable {
    private ServerClientThread serverClientThread;

    @Override
    public void execute(ServerClientThread sct) {
        ServerClientThread.Helper helper = ServerClientThread.Helper.getInstance();
        StringBuilder productSB = new StringBuilder();
        int id = 0;
        productSB.append('|');
        for (Product product : sct.getAuctionHouse().getProductsList()) {
            id++;
            productSB.append(id);
            productSB.append(") ");
            productSB.append(product.toString());
            productSB.append('|');
        }
        helper.setCommandResult(helper.getCommandResult().append(productSB));
    }

    @Override
    public void setSct(ServerClientThread sct) {
        this.serverClientThread = sct;
    }

    @Override
    public void run() {
        this.execute(this.serverClientThread);
    }
}
