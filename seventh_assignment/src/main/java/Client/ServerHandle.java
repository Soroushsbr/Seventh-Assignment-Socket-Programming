package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandle implements Runnable {
    private DataInputStream in;
    private String name;
    public ServerHandle(Socket client , ArrayList<String> msg) throws IOException{
        this.in = new DataInputStream(client.getInputStream());
        this.name = name;
        //todo: get name of client
    }
    @Override
    public void run() {
        try{
            while (true){
                System.out.println(this.in.readUTF());
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
