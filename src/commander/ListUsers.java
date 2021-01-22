package commander;


import loginsql.MySQLConnection;
import socketserver.ClientMain;
import socketserver.Main;
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
            out.println("DA");
            ClientMain.auctionHouse.getUserList().forEach(
                    out::println
            );
        }

    }
}
