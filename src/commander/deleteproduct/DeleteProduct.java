package commander.deleteproduct;

import commander.ICommand;
import loginsql.MySQLConnection;
import products.Product;
import socketserver.ServerClientThread;

import java.util.List;

public class DeleteProduct implements ICommand {
    private final int productId;

    public DeleteProduct(int productId) {
        this.productId = productId;
    }

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
            DeleteFromDB.deleteFromDB(productId, mySqlConnection);
        } else {
            sb.append("Access Denied you are not Admin! You cannot delete any product!");
        }
        ServerClientThread.Helper resultCommand = ServerClientThread.Helper.getInstance();
        resultCommand.setCommandResult(resultCommand.getCommandResult().append(sb));
    }
}
