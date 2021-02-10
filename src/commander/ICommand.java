package commander;

import socketserver.ServerClientThread;

/**
 * interface of commands
 */
public interface ICommand extends Runnable{
    void execute(ServerClientThread serverClientThread);
    void setSct(ServerClientThread sct);
}
