import java.io.*;
import java.net.*;
import java.util.*;

public class TicTacToeServer {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private List<PlayerHandler> players;

    public TicTacToeServer() {
        players = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for players to connect...");
            acceptPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptPlayers() {
        while (true) {
            try {
                // Accept two players
                Socket player1Socket = serverSocket.accept();
                System.out.println("Player 1 connected");

                Socket player2Socket = serverSocket.accept();
                System.out.println("Player 2 connected");

                // Create player handlers and start a new game
                PlayerHandler player1 = new PlayerHandler(player1Socket, "X");
                PlayerHandler player2 = new PlayerHandler(player2Socket, "O");

                players.add(player1);
                players.add(player2);

                // Start the game with these two players
                startGame(player1, player2);
            } catch (IOException e) {
                System.err.println("Error accepting player connections: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void startGame(PlayerHandler player1, PlayerHandler player2) {
        new Thread(() -> {
            try {
                // Game logic goes here. You can call your TicTacToe class here to play the game.
                TicTacToe game = new TicTacToe(player1.getDataInputStream(), player1.getDataOutputStream(), player2.getDataInputStream(), player2.getDataOutputStream());
                game.startGame();

                // After game ends, close the players' sockets and reset
                player1.getSocket().close();
                player2.getSocket().close();
                System.out.println("Game over. Waiting for new players.");
            } catch (IOException e) {
                System.err.println("Error during game session: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }


    public static void main(String[] args) {
        new TicTacToeServer();
    }

    // Player handler class for managing individual player connections
    private static class PlayerHandler {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private String mark;

        public PlayerHandler(Socket socket, String mark) {
            this.socket = socket;
            this.mark = mark;
            try {
                this.in = new DataInputStream(socket.getInputStream());
                this.out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getMark() {
            return mark;
        }

        public Socket getSocket() {
            return socket;
        }

        public DataInputStream getDataInputStream() {
            return in;
        }

        public DataOutputStream getDataOutputStream() {
            return out;
        }
    }
}
