

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameHandler implements Runnable {
    private static Socket player1, player2;
    private static DataInputStream player1input, player2input;
    private static DataOutputStream player1output, player2output;
    ExecutorService pool = Executors.newFixedThreadPool(10);

    public GameHandler(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2; // Assuming two players are always playing.

    }

    @Override
    public void run() {

        try {

            player1input = new DataInputStream(player1.getInputStream());
            player1output = new DataOutputStream(player1.getOutputStream());

            System.out.println("Player 2 joined");
            player2input = new DataInputStream(player2.getInputStream());
            player2output = new DataOutputStream(player2.getOutputStream());

            pool.execute(() -> new TicTacToe(player1input, player1output, player2input, player2output));

            System.out.println("Started a game!");

        } catch (IOException e) {
            e.getMessage();
        }
    }

}
