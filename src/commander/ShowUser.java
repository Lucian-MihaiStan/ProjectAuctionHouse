package commander;

import socketserver.ServerClientThread;

/**
 * display username about the current user
 */
public class ShowUser implements ICommand, Runnable {
    private ServerClientThread serverClientThread;

    /**
     * implements the execution of command
     * @param sct thread were the message should be printed
     */
    @Override
    public synchronized void execute(ServerClientThread sct) {
        ServerClientThread.Helper result = ServerClientThread.Helper.getInstance();
        result.setCommandResult(result.getCommandResult().append(sct.getMySQLConnection().getUsername()));
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
        this.execute(serverClientThread);
    }
}
