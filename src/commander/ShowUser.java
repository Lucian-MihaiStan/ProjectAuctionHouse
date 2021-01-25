package commander;

import socketserver.ServerClientThread;

public class ShowUser implements ICommand {

    @Override
    public void execute(ServerClientThread sct) {
        ServerClientThread.Helper result = ServerClientThread.Helper.getInstance();
        result.setCommandResult(result.getCommandResult().append(sct.mySQLConnection.getUsername()));
    }
}
