// Server Class
package Server;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    // TODO: Implement the server-side operations

    // TODO: Add constructor and necessary methods
    private static final int port = 3000;
    private static ArrayList<Socket> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(8);
    private static ExecutorService poolFile = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int history = 0;
        while (true) {
            System.out.println("set a number for how many massages a Client is able see([0] All): ");
            history = scanner.nextInt();
            if(history >= 0){
                break;
            }else {
                System.out.println("Set a positive number include 0:");
            }
        }
        ServerSocket listen = null;
        try{
            listen = new ServerSocket(port);
            System.out.println("[SERVER] Server started. Waiting for client connections...");
            while (true){
                Socket client = listen.accept();
                DataInputStream in = new DataInputStream(client.getInputStream());
                String op = in.readUTF();
                if(op.equals("1")){
                    System.out.println("[SERVER] Connected to client [CHAT]: " + client.getInetAddress());
                    ClientHandle clientHandle = new ClientHandle(client, clients, history);
                    clients.add(client);
                    pool.execute(clientHandle);
                }else if(op.equals("2")){
                    System.out.println("[SERVER] Connected to client [File]: " + client.getInetAddress());
                    FileHandle fileHandle = new FileHandle(client);
                    poolFile.execute(fileHandle);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(listen != null){
                try{
                    listen.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            pool.shutdown();
        }

    }
}