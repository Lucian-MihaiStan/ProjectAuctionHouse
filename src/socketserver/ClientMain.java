package socketserver;

import java.io.*;
import java.net.Socket;
import static java.lang.System.*;

public class ClientMain {

    public static void main(String[] args) {
        String serverMessage;
        String clientMessage = "";
        try(Socket socket = new Socket("localhost", 4999)) {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            while(!clientMessage.equals("exit")) {
                out.println("");
                clientMessage = bufferedReader.readLine();
                outputStream.writeUTF(clientMessage);
                outputStream.flush();
                serverMessage = inputStream.readUTF();
                out.println(serverMessage);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
