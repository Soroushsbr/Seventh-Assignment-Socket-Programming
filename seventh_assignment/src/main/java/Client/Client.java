// Client Class
package Client;

import Server.FileHandle;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println("(1) Group Chat \n(2) Download File\n(3) Exit");
        while (true){
            switch (reader.readLine()){
                case "1":
                    chat();
                    break;
                case "2":
                    downloadFile();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    public static void chat () throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Socket client = new Socket(server_IP , server_port);
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeUTF("1"); //to tell the server which one the client chose
        out.writeUTF(name + " connected to server.");

        ServerHandle serverHandle = new ServerHandle(client , new ArrayList<>());
        new Thread(serverHandle).start();
        String userInput;

        while (true){
            userInput = "[" + name + "]: " + reader.readLine();
            if(userInput.equals(name + ": " + null)){
                out.writeUTF(name + " Left the Server.");
                break;
            }
            out.writeUTF(userInput);
        }
    }

    public static void downloadFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Socket client = new Socket(server_IP , server_port);
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeUTF("2");      //to tell the server which one the client chose

        ServerHandle serverHandle = new ServerHandle(client , new ArrayList<>());
        new Thread(serverHandle).start();

        String userInput = reader.readLine();
        out.writeUTF(userInput);
//        byte[] byteArray = new byte[1024];
//        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\Java Projects\\Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\java\\Client");
//        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
//
//        int bytesRead = in.read(byteArray, 0, byteArray.length);
//        bos.write(byteArray, 0, bytesRead);
//        bos.close();
    }
}