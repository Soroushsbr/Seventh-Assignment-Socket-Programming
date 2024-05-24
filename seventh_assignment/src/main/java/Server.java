// Server Class

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    // TODO: Implement the server-side operations

    // TODO: Add constructor and necessary methods
    private static final int port = 3000;
    private static ArrayList<Socket> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        ServerSocket listen = null;
        try{
            listen = new ServerSocket(port);
            System.out.println("[SERVER] Server started. Waiting for client connections...");
            while (true){
                Socket client = listen.accept();
                System.out.println("[SERVER] Connected to client: " + client.getInetAddress());
                ClientHandle clientHandle = new ClientHandle(client , clients);
                clients.add(client);
                pool.execute(clientHandle);
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