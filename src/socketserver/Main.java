package socketserver;

import auction_house.AuctionHouse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.*;

public class Main {

    private static final AuctionHouse auctionHouse = AuctionHouse.getInstance();

    public static void main(String[] args) {
        auctionHouse.loadAsAdmin();
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
