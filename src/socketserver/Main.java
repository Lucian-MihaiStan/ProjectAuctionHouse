package socketserver;

import auction.Auction;
import auction_house.AuctionHouse;
import loginsql.MySQLConnection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.*;

public class Main {

    public static final Random random = new Random();

    public static final AuctionHouse auctionHouse = AuctionHouse.getInstance();

    public static final List<ServerClientThread> sctList = new ArrayList<>();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.realizeConnectionAsAdmin();
        auctionHouse.load(mySQLConnection);
        mySQLConnection.closeConnection();

        try(ServerSocket serverSocket = new ServerSocket(4999)) {
            int counter = 0;
            serverSocket.setReuseAddress(true);
            out.println("Server Started....");
            while (true) {
                counter++;
                Socket client = serverSocket.accept();
                String clientIP = client.getInetAddress().getHostAddress();
                out.println(">> Client No: " + counter + " with IP: " + client
                        + " started!");
                ServerClientThread socketClientThread = new ServerClientThread(client, counter, clientIP);
                sctList.add(socketClientThread);
                notifyCheck(sctList);
                socketClientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void notifyCheck(List<ServerClientThread> sctList) {
        List<Auction> auctionList = auctionHouse.getAuctionsActive();
        auctionList.forEach(auction -> {

        });
    }
}
