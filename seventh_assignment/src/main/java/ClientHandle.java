import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandle implements Runnable {
    public static ArrayList<String> massages = new ArrayList<>();
    private Socket client; // The client socket
    private static ArrayList<Socket> clients; // List of all connected clients
    private DataInputStream in; // Input stream to receive data from the client
    private DataOutputStream out; // Output stream to send data to the client

    public ClientHandle(Socket client , ArrayList<Socket> clients)throws IOException {
        this.client =client;
        this.clients = clients;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
        //todo: maybe should add name here
    }

    @Override
    public void run() {
        try{
            String request;
            while (true){
                request = this.in.readUTF();
//                massages.add(request);
                System.out.println(massages);
                sendToAll(request);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendToAll(String msg) throws IOException{
        for(Socket client : clients){
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(msg);
        }
    }
}
