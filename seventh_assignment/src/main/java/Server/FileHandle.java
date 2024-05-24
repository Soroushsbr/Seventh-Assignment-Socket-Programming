package Server;

import java.io.*;
import java.net.Socket;

public class FileHandle implements Runnable{
    private Socket client;
    private DataInputStream in; // Input stream to receive data from the client
    private DataOutputStream out; // Output stream to send data to the client
    public FileHandle(Socket client) throws IOException {
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
    }
    @Override
    public void run() {
        try {
            File folder = new File("src/main/java/Server/data");
//            File folder = new File("C:\\Users\\Lenovo\\Desktop\\Java Projects\\Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\java\\Server\\data");
            File[] filesList = folder.listFiles();
            String list = "";
            if(filesList != null){
                int i = 1;
                for (File file : filesList) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        list = list + "(" + i + ") " + file.getName() + "\n";
                        i++;
                    }
                }
            }
            out.writeUTF(list);
            String request =in.readUTF();
            int req = Integer.parseInt(request) - 1;
            assert filesList != null;
            File chosenFile = filesList[req];
            File targetFolder = new File("src/main/java/Client");
//            File targetFolder = new File("C:\\Users\\Lenovo\\Desktop\\Java Projects\\Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\java\\Client");
            boolean success = chosenFile.renameTo(new File(targetFolder, chosenFile.getName()));
            if (success) {
                out.writeUTF("File downloaded successfully!");
            } else {
                out.writeUTF("File download failed.");
            }
//            byte[] byteArray = new byte[(int) chosenFile.length()];
//            BufferedInputStream BIS = new BufferedInputStream(new FileInputStream(chosenFile));
//            BIS.read(byteArray , 0 , byteArray.length);
//            out.write(byteArray, 0 , byteArray.length);
//            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
