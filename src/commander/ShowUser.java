package commander;

import socketserver.ServerClientThread;

public class ShowUser implements ICommand, Runnable {
    private ServerClientThread serverClientThread;

    @Override
    public synchronized void execute(ServerClientThread sct) {
        ServerClientThread.Helper result = ServerClientThread.Helper.getInstance();
        result.setCommandResult(result.getCommandResult().append(sct.getMySQLConnection().getUsername()));
    }

    @Override
    public void setSct(ServerClientThread sct) {
        this.serverClientThread = sct;
    }

    @Override
    public void run() {
        this.execute(serverClientThread);
    }
}
