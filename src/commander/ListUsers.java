package commander;

import loginsql.MySQLConnection;
import socketserver.ClientMain;
import socketserver.ServerClientThread;

import static java.lang.System.*;

public class ListUsers implements ICommand {
    @Override
    public void execute() {

        MySQLConnection mySQLConnection = ServerClientThread.mySQLConnection;

        if(!mySQLConnection.getUsername().equals("admin")) {
            out.println("Access Denied");
        }
        else {
            /*ClientMain.dataFlow = new StringBuilder();
            ClientMain.auctionHouse.getUserList().forEach(user ->
                ClientMain.dataFlow.append(user.toString())
            );*/
        }

    }
}
