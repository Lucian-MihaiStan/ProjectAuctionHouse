package socketserver;

import auction_house.AuctionHouse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.*;

public class Main {

    // String builder sa trimiti mesaj la client in loc de text

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(4999)) {
            int counter = 0;
            out.println("Server Started....");
            while(true){
                counter++;
                Socket serverClient = serverSocket.accept();
                out.println(">> Client No: " + counter + " started!");
                ServerClientThread sct = new ServerClientThread(serverClient, counter);

                /* comunica cu serverul */
                OutputStream outputStream = serverClient.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream, true);

                String text = "Ala bala portocala";
                writer.println(text);

                sct.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
