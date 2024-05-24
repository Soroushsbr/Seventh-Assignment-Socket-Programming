// Client Class

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    // TODO: Implement the client-side operations

    // TODO: Add constructor and necessary methods
    private static final String server_IP = "127.0.0.1";
    private static final int server_port = 3000;
    private static String name;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        name = reader.readLine();

        Socket client = new Socket(server_IP , server_port);
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeUTF(name + " connected to server.");

        ServerHandle serverHandle = new ServerHandle(client , new ArrayList<>());
        new Thread(serverHandle).start();
        String userInput;

        while (true){
            userInput = name + ": " + reader.readLine();
            if(userInput.equals(name + ": " + null)){
                out.writeUTF(name + " Left the Server.");
                break;
            }
            out.writeUTF(userInput);
        }
    }
}