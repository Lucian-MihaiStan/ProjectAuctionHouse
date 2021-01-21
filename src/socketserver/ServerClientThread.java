package socketserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.System.*;

public class ServerClientThread extends Thread {
    Socket serverClient;
    int clientNo;
    public ServerClientThread(Socket inSocket,int counter){
        serverClient = inSocket;
        clientNo=counter;
    }

    @Override
    public void run(){
        String clientMessage = "";
        String serverMessage = "";
        try(DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream())){
            while(!clientMessage.equals("exit")){
                clientMessage=inStream.readUTF();
                out.println("From Client-" +clientNo+ ": Number is :"+clientMessage);
                serverMessage = "From Server to Client-" + clientNo + " Square of " + clientMessage + " is ";
                outStream.writeUTF(serverMessage);
                outStream.flush();
            }
            serverClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            out.println("Client -" + clientNo + " exit!! ");
        }
    }
}
