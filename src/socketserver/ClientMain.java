package socketserver;

//import auction_house.AuctionHouse;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.*;

public class ClientMain {

//    public static final AuctionHouse auctionHouse = AuctionHouse.getInstance().load();

    public static void main(String[] args) {
        String serverMessage;
        String clientMessage = "";

        boolean accountClient = loginMessages();

        try(Socket socket = new Socket("localhost", 4999)) {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream()); // input de la server
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()); // trimit la server

            InputStream input = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

            while(!clientMessage.equalsIgnoreCase("exit")) {
                out.print(">> ");
                clientMessage = bufferedReader.readLine();
                /* primeste de la server */
                out.println(clientMessage + " nu stiu ce e asta ");
                outputStream.writeUTF(clientMessage);
                outputStream.flush();
                serverMessage = inputStream.readUTF(); // ?! cred ca citesc input de la server
                out.println(serverMessage + " DADADA ");

                BufferedReader userInput = new BufferedReader(new InputStreamReader(in));
                out.println("Enter a command");
                String commandUser = userInput.readLine();
                ServerClientThread.commandUser =  commandUser;

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
