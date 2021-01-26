package socketserver;

import auction_house.AuctionHouse;
import loginsql.MySQLConnection;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static commander.Caller.addCommand;
import static commander.Caller.executeCommands;
import static java.lang.System.*;

public class ServerClientThread extends Thread {
    private final Socket serverClient;
    private final int clientNo;
    private final String hostAddress;
    private String notifier;
    private PrintWriter outWriterConsole;

    public void setNotifier(String notifier) {
        outWriterConsole.println(notifier);
    }

    public static class Helper {
        private static Helper instance;
        private StringBuilder commandResult = new StringBuilder();

        public static Helper getInstance() {
            if(instance == null) {
                instance = new Helper();
            }
            return instance;
        }

        private Helper() {}
        public StringBuilder getCommandResult() {
            return commandResult;
        }

        public void setCommandResult(StringBuilder commandResult) {
            this.commandResult = commandResult;
        }
    }

    public ServerClientThread(Socket inSocket, int counter, String hostAddress){
        this.serverClient = inSocket;
        this.clientNo = counter;
        this.hostAddress = hostAddress;
    }

    private final MySQLConnection mySQLConnection = new MySQLConnection();

    public MySQLConnection getMySQLConnection() {
        return mySQLConnection;
    }

    private static final AuctionHouse auctionHouse = Main.auctionHouse;

    public void addCommandToList(List<String> parameters) {
        addCommand(parameters, auctionHouse);
    }

    @Override
    public void run(){
        BufferedReader inBR;
        StringBuilder result = null;
        try(PrintWriter outWriter = new PrintWriter(serverClient.getOutputStream(), true)) {
             inBR = new BufferedReader(
                    new InputStreamReader(serverClient.getInputStream()));
            String commandUserBR;
            this.outWriterConsole = outWriter;
            while((commandUserBR = inBR.readLine()) != null) {
                out.println("   Sent from the client " + clientNo + " " + hostAddress + " " + commandUserBR);
                evalCommand(commandUserBR);
                if("execute".equalsIgnoreCase(commandUserBR.split(" ")[0])) {
                    executeCommands(this, auctionHouse);
                    result = Helper.getInstance().getCommandResult();
                    Helper.getInstance().setCommandResult(new StringBuilder());
                }
                if(notifier != null) {
                    if(result == null) result = new StringBuilder();
                    result.append(notifier);
                    notifier = null;
                }
                if(result!=null) outWriter.println(result);
                else outWriter.println("Instruction registered");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void evalCommand(String commandUser) {
        List<String> commandParams = Arrays.asList(commandUser.split(" "));
        if("login".equalsIgnoreCase(commandParams.get(0))) {
            try {
                mySQLConnection.realizeConnection(commandParams.get(1), commandParams.get(2));
                auctionHouse.load(mySQLConnection);
            } catch (SQLException | ClassNotFoundException errorSQL) {
                errorSQL.printStackTrace();
            }
        }
        else if(!"EXECUTE".equalsIgnoreCase(commandParams.get(0))) {
            addCommandToList(commandParams);
        }
    }

    public AuctionHouse getAuctionHouse() {
        return auctionHouse;
    }
}
