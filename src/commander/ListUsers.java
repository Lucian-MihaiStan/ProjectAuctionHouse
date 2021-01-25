package commander;

import loginsql.MySQLConnection;
import socketserver.ServerClientThread;

import static java.lang.System.*;

public class ListUsers implements ICommand {
    @Override
    public void execute(ServerClientThread sct) {
        MySQLConnection mySQLConnection = sct.mySQLConnection;

        ServerClientThread.Helper helper = ServerClientThread.Helper.getInstance();

        if(!("admin").equals(mySQLConnection.getUsername())) {
            helper.setCommandResult(helper.getCommandResult().append("Access Denied to see all users"));
        }
        else {
            StringBuilder usersSB = new StringBuilder();
            sct.getAuctionHouse().getUserList().forEach(
                    user -> usersSB.append(user.toString())
            );
            helper.setCommandResult(helper.getCommandResult().append(usersSB));
        }
    }
}
