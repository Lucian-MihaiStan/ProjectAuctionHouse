package commander;

import loginsql.MySQLConnection;
import socketserver.ServerClientThread;


public class ListUsers implements ICommand {
    @Override
    public void execute() {

        MySQLConnection mySQLConnection = ServerClientThread.mySQLConnection;

        ServerClientThread.Helper helper = ServerClientThread.Helper.getInstance();
        if(!("admin").equals(mySQLConnection.getUsername())) {
            helper.setCommandResult(helper.getCommandResult().append("Access Denied to see all users"));
        }
        else {
            StringBuilder usersSB = new StringBuilder();
            ServerClientThread.auctionHouse.getUserList().forEach(
                    user -> usersSB.append(user.toString())
            );
            helper.setCommandResult(helper.getCommandResult().append(usersSB));
        }
    }
}
