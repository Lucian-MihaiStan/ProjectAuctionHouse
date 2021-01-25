package commander;

import socketserver.ServerClientThread;

public class ShowUser implements ICommand {

    @Override
    public void execute() {
        ServerClientThread.Helper result = ServerClientThread.Helper.getInstance();
        result.setCommandResult(result.getCommandResult().append(ServerClientThread.mySQLConnection.getUsername()));
    }
}
