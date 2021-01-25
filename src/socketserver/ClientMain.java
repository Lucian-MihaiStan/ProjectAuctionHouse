package socketserver;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.*;

public class ClientMain {

    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 4999)) {
            PrintWriter printWriter = new PrintWriter(
                    socket.getOutputStream(), true);
            BufferedReader inBR =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner clientScanner = new Scanner(in);
            String line = null;
            while(!"exit".equalsIgnoreCase(line)) {
                line = clientScanner.nextLine();
                printWriter.println(line);
                printWriter.flush();
                if("exit".equalsIgnoreCase(line)) break;
                String repliedServer = inBR.readLine();
                if("execute".equalsIgnoreCase(line))
                    out.println("Server replied " + repliedServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean loginMessages() {
        out.println("|>> Welcome to Royal Auction House!");
        out.println("|>> Do you have an account? YES/NO");
        Scanner clientScanner = new Scanner(in);
        String answer = clientScanner.nextLine();
        if(answer.equalsIgnoreCase("NO")) {
            out.println("|>> Do you want to create an account? YES/NO");
            String yesNoAnswer = clientScanner.nextLine();
            if(yesNoAnswer.equalsIgnoreCase("NO")) closeApp();
            return false;
        }
        return true;
    }

    private static void closeApp() {
        out.println("|>> We are sorry, I hope you will return!");
        System.exit(-1);
    }
}
