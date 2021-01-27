package commander;

import loginsql.MySQLConnection;
import socketserver.ServerClientThread;

public class ListUsers implements ICommand {
    @Override
    public void execute(ServerClientThread sct) {
        synchronized (sct.getMySQLConnection()) {

            MySQLConnection mySQLConnection = sct.getMySQLConnection();

            ServerClientThread.Helper helper = ServerClientThread.Helper.getInstance();

            if (!("admin").equals(mySQLConnection.getUsername())) {
                helper.setCommandResult(helper.getCommandResult().append("Access Denied to see all users"));
                return;
            }
            StringBuilder usersSB = new StringBuilder();
            usersSB.append('|');
            sct.getAuctionHouse().getUserList().forEach(
                    user -> {
                        usersSB.append(user.toString());
                        usersSB.append('|');
                    }
            );
            helper.setCommandResult(helper.getCommandResult().append(usersSB));
        }
    }
}
