package commander;

import loginsql.MySQLConnection;
import socketserver.ServerClientThread;

/**
 * display information about users
 */
public class ListUsers implements ICommand, Runnable {
    private ServerClientThread serverClientThread;

    /**
     * implements the execution of command
     * @param sct thread were the message should be printed
     */
    @Override
    public void execute(ServerClientThread sct) {
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
