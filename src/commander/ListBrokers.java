package commander;

import socketserver.ServerClientThread;

public class ListBrokers implements ICommand {
    @Override
    public void execute(ServerClientThread serverClientThread) {
        ServerClientThread.Helper resultCommand = ServerClientThread.Helper.getInstance();
        if(!"admin".equals(serverClientThread.getMySQLConnection().getUsername())) {
            resultCommand.setCommandResult(resultCommand.getCommandResult().append("|").append("Access Denied").append("|"));
            return;
        }
        StringBuilder cmdRes = new StringBuilder();
        cmdRes.append('|');
        serverClientThread.getAuctionHouse().getBrokersList().forEach(broker ->{
            cmdRes.append(broker.toString());
            cmdRes.append('|');
        });
        resultCommand.setCommandResult(resultCommand.getCommandResult().append(cmdRes));
    }
}
