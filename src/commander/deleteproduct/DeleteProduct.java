package commander.deleteproduct;

import commander.ICommand;
import loginsql.MySQLConnection;
import products.Product;
import socketserver.ServerClientThread;

import java.util.List;

/**
 * delete product
 */
public class DeleteProduct implements ICommand, Runnable {
    private final int productId;
    private ServerClientThread serverClientThread;

    /**
     * delete product command
     * @param productId id of product
     */
    public DeleteProduct(int productId) {
        this.productId = productId;
    }

    /**
     * implements the execution of command
     * @param sct thread were the message should be printed
     */
    @Override
    public synchronized void execute(ServerClientThread sct) {
        MySQLConnection mySqlConnection = sct.getMySQLConnection();
        String username = mySqlConnection.getUsername();
        StringBuilder sb = new StringBuilder();
        if ("admin".equals(username)) {
            sb.append("Delete command released with success");
            List<Product> productList = sct.getAuctionHouse().getProductsList();
            productList.removeIf(product -> product.getId() == productId);
            sct.getAuctionHouse().setProductsList(productList);
            DeleteProductDB.deleteFromDB(productId, mySqlConnection);
        } else {
            sb.append("Access Denied you are not Admin! You cannot delete any product!");
        }
        ServerClientThread.Helper resultCommand = ServerClientThread.Helper.getInstance();
        resultCommand.setCommandResult(resultCommand.getCommandResult().append(sb));
    }

    @Override
    public void setSct(ServerClientThread sct) {
        this.serverClientThread = sct;
    }

    /**
     * run method from runnable interface used for multithreading
     */
    @Override
    public void run() {
        this.execute(this.serverClientThread);
    }
}
