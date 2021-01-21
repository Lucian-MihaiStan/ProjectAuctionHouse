package socketserver;

import loginsql.MySQLConnection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.*;
import static commander.Caller.*;

public class Main {
    public static final MySQLConnection mySQLConnection = MySQLConnection.getInstance();

    public static void addCommandToList(String commandLine) {
        List<String> parameters = Arrays.asList(commandLine.split(" "));
        addCommand(parameters);
    }

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(4999)) {
            int counter = 0;
            out.println("Server Started....");
            while(true){
                counter++;
                Socket serverClient = serverSocket.accept();
                out.println(">> Client No: " + counter + " started!");
                ServerClientThread sct = new ServerClientThread(serverClient, counter);
                sct.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
