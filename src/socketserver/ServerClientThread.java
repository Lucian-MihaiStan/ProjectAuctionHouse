package socketserver;

import loginsql.MySQLConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static commander.Caller.addCommand;
import static commander.Caller.executeCommands;
import static java.lang.System.*;

public class ServerClientThread extends Thread {

    public static final MySQLConnection mySQLConnection = MySQLConnection.getInstance();

    public static void addCommandToList(String commandLine) {
        List<String> parameters = Arrays.asList(commandLine.split(" "));
        addCommand(parameters);
    }

    private final Socket serverClient;
    private final int clientNo;
    public ServerClientThread(Socket inSocket, int counter){
        serverClient = inSocket;
        clientNo = counter;
    }

    @Override
    public void run(){
        String clientMessage = "";
        String serverMessage;
        boolean connection = false;
        try(DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream())){
            while(!clientMessage.equalsIgnoreCase("exit")){
                clientMessage = inStream.readUTF();
                List<String> clientCommand = Arrays.asList(clientMessage.split(" "));
                if(!connection){
                    try {
                        mySQLConnection.realizeConnection(clientCommand.get(1), clientCommand.get(2));
                        connection = true;
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else {
                    if(clientMessage.equalsIgnoreCase("logout")) {
                        mySQLConnection.closeConnection();
                        break;
                    }
                    if(clientCommand.get(0).equalsIgnoreCase("execute")) {
                        executeCommands();
                    }
                    else {
                        addCommandToList(clientMessage);
                    }
                }
                serverMessage = clientMessage + " command released with successfully";
                outStream.writeUTF(serverMessage);
                outStream.flush();
            }
            serverClient.close();
        } catch (EOFException e) {
            //
        } catch (IOException e) {
            err.println("<< !! Client " + clientNo + " closed connection forced!");
        } finally{
            out.println("<< !! Client " + clientNo + " closed connection");
        }
    }
}
