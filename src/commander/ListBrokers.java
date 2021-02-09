package commander;

import employee.Broker;
import socketserver.ServerClientThread;

import java.util.Map;

public class ListBrokers implements ICommand, Runnable {
    private ServerClientThread serverClientThread;

    @Override
    public void execute(ServerClientThread sct) {
        ServerClientThread.Helper resultCommand = ServerClientThread.Helper.getInstance();
        if(!"admin".equals(sct.getMySQLConnection().getUsername())) {
            resultCommand.setCommandResult(resultCommand.getCommandResult().append("|").append("Access Denied").append("|"));
            return;
        }
        StringBuilder cmdRes = new StringBuilder();
        cmdRes.append('|');

        Map<Integer, Broker> brokers = sct.getAuctionHouse().getBrokers();
        brokers.forEach((integer, broker) -> {
            cmdRes.append(broker.toString());
            cmdRes.append('|');
        });

//        serverClientThread.getAuctionHouse().getBrokersList().forEach(broker ->{
//            cmdRes.append(broker.toString());
//            cmdRes.append('|');
//        });
        resultCommand.setCommandResult(resultCommand.getCommandResult().append(cmdRes));
    }

    @Override
    public void setSct(ServerClientThread sct) {
        this.serverClientThread = sct;
    }

    @Override
    public void run() {
        this.execute(this.serverClientThread);
    }
}
