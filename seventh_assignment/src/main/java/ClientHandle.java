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
    private int history;

    public ClientHandle(Socket client , ArrayList<Socket> clients , int history)throws IOException {
        this.client =client;
        this.clients = clients;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
        this.history = history;
        sendToOne();

        //todo: maybe should add name here
    }

    public static void showPerChat(){

    }

    @Override
    public void run() {
        try{
            String request;
            while (true){
                request = this.in.readUTF();
                massages.add(request);
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

    public void sendToOne()throws IOException{
        String historyMsg = "";
        if(history == 0){
            history = massages.size();
        }
        try {
            for (int i = massages.size() - history; i < massages.size(); i++) {
                historyMsg = historyMsg + massages.get(i) + "\n";
            }
            DataOutputStream out = new DataOutputStream(this.client.getOutputStream());
            if (historyMsg != null) {
                out.writeUTF(historyMsg);
            }
        }catch (IndexOutOfBoundsException ignored){
        }
    }

    private void sendToAll(String msg) throws IOException{
        for(Socket client : clients){
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(msg);
        }
    }
}
