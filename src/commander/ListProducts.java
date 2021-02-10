package commander;

import products.Product;
import socketserver.ServerClientThread;

/**
 * display all products command
 */
public class ListProducts implements ICommand, Runnable {
    private ServerClientThread serverClientThread;

    /**
     * implements the execution of command
     * @param sct thread were the message should be printed
     */
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

    /**
     * method run that implements runnable
     */
    @Override
    public void run() {
        this.execute(this.serverClientThread);
    }
}
