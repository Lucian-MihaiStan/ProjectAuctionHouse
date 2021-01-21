package client_main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.out;

public class ClientMain {
    public static void main(String[] args) {
        out.println("> Client started");
        out.println("> Welcome to Royal Auction House");
        while(true) {
            try (Socket s = new Socket("localhost", 4999)) {
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                out.print("> ");
                String str = userInput.readLine();
                PrintWriter outServer = new PrintWriter(s.getOutputStream(), true);
                outServer.println(str);
                if(str.equals("EXIT")) break;
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out.println(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
