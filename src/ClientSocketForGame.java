import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ClientSocketForGame {
    private final static String SERVER_ADDRESS = "127.0.0.1";  // Replace with your server IP address.
    private final static int PORT = 12345;

    public static void main(String[] args) throws IOException {
//    public SocketForGame() throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);) {

            System.out.println("Connected to the server!");

            DataOutputStream secPlayerOut = new DataOutputStream(socket.getOutputStream());
            DataInputStream secPlayerIn = new DataInputStream(socket.getInputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());



        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error connecting to the server.");
        }


    }
}

