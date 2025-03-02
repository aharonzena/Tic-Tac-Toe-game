import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketForGame {
    private static final int PORT = 8050;
    private static Socket player1, player2;
    protected static ServerSocket serverSocket;


    public static void main(String[] args) throws IOException {

        serverSocket = new ServerSocket(PORT);

        System.out.println("Server started, and waiting for players to connect");

        while (true) {
            try{
                ExecutorService pool = Executors.newFixedThreadPool(10);
                System.out.println("waiting for players");

                player1 = serverSocket.accept();
                System.out.println("player 1 joined");
                player2 = serverSocket.accept();
                System.out.println("Player connected");


                pool.execute(new GameHandler(player1, player2));

            }catch (IOException e) {
                e.getMessage();
            }
        }
    }
}

