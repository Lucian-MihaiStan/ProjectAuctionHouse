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
    private Socket serverClient;
    private int clientNo;
    private String hostAddress;

    /* nusj ce e asta dar il las aici*/
    public static String commandUser;

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

        public void printCommand() {
            out.println(commandResult);
        }
    }

    public ServerClientThread(Socket inSocket, int counter, String hostAddress){
        this.serverClient = inSocket;
        this.clientNo = counter;
        this.hostAddress = hostAddress;
    }

    public static final MySQLConnection mySQLConnection = MySQLConnection.getInstance();

    public static void addCommandToList(List<String> parameters) {
        addCommand(parameters);
    }

    public static final AuctionHouse auctionHouse = AuctionHouse.getInstance().load();

    @Override
    public void run(){
        BufferedReader inBR;
        StringBuilder result = null;
        try(PrintWriter outWriter = new PrintWriter(serverClient.getOutputStream(), true)) {
             inBR = new BufferedReader(
                    new InputStreamReader(serverClient.getInputStream()));
            String commandUserBR;
            while((commandUserBR = inBR.readLine()) != null) {
                out.println("   Sent from the client " + commandUserBR);
                evalCommand(commandUserBR);
                if("execute".equalsIgnoreCase(commandUserBR.split(" ")[0])) {
                    executeCommands();
                    result = Helper.getInstance().getCommandResult();
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
            } catch (SQLException | ClassNotFoundException errorSQL) {
                errorSQL.printStackTrace();
            }
        }
        else if(!"EXECUTE".equalsIgnoreCase(commandParams.get(0))) {
            out.println(commandUser);
            addCommandToList(commandParams);
        }
    }
}
