package commander;

import socketserver.ServerClientThread;

public interface ICommand extends Runnable{
    void execute(ServerClientThread serverClientThread);
    void setSct(ServerClientThread sct);
}
