package socketserver;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

/**
 * main of client
 * this class si run by any client at the start of program
 */
public class ClientMain {

    /**
     * main entrance of program
     * @param args arguments of main
     */
    public static void main(String[] args) {
        loginMessages();
        try(Socket socket = new Socket("localhost", 4999)) {
            PrintWriter printWriter = new PrintWriter(
                    socket.getOutputStream(), true);
            BufferedReader inBR =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner clientScanner = new Scanner(in);
            String line = null;
            while(!"exit".equalsIgnoreCase(line)) {
                out.print(">> ");
                line = clientScanner.nextLine();
                printWriter.println(line);
                printWriter.flush();
                if("exit".equalsIgnoreCase(line)) break;
                String repliedServer = inBR.readLine();
                if("execute".equalsIgnoreCase(line))
                    out.println("Server replied " + splitMessage(repliedServer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String splitMessage(String repliedServer) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = Arrays.asList(repliedServer.split("\\|"));
        stringList.forEach(line -> {
            stringBuilder.append(line);
            stringBuilder.append('\n');
        });
        return stringBuilder.toString();
    }

    private static void loginMessages() {
        out.println("|>> Welcome to Royal Auction House!");
    }
}
