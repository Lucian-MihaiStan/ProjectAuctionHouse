package socketserver;

import auction_house.AuctionHouse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.*;

public class Main {

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(4999)) {
            int counter = 0;
            serverSocket.setReuseAddress(true);
            out.println("Server Started....");
            while(true){
                counter++;
                Socket client = serverSocket.accept();
                String clientIP = client.getInetAddress().getHostAddress();
                out.println(">> Client No: " + counter + " with IP: " + client
                         + " started!");
                ServerClientThread socketClientThread = new ServerClientThread(client, counter, clientIP);
                new Thread(socketClientThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
