package socketserver;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.*;

public class ClientMain {

    public static void main(String[] args) {
        String serverMessage;
        String clientMessage = "";

        boolean accountClient = loginMessages();

        try(Socket socket = new Socket("localhost", 4999)) {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            while(!clientMessage.equalsIgnoreCase("exit")) {
                out.print(">> ");
                clientMessage = bufferedReader.readLine();
                outputStream.writeUTF(clientMessage);
                outputStream.flush();
                serverMessage = inputStream.readUTF();
                out.println(serverMessage);
            }
            inputStream.close();
            outputStream.close();
        } catch (EOFException e) {
            err.println("EOFException found");
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
