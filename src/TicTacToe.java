import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    private DataInputStream player1In, player2In;
    private DataOutputStream player1out, player2out;

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel Title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel TextField = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1Turn = true;
    boolean player2Turn = false;

    //    boolean player2Turn = true;
    public TicTacToe(DataInputStream player1input, DataOutputStream player1output, DataInputStream player2input, DataOutputStream player2output) {
        this.player1In = player1input;
        this.player1out = player1output;
        this.player2In = player2input;
        this.player2out = player2output;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        TextField.setBackground(Color.cyan);
        TextField.setForeground(Color.GRAY);
        TextField.setText("Player 1's Turn");
        TextField.setFont(new Font("Arial", Font.BOLD, 24));
        TextField.setHorizontalAlignment(JLabel.CENTER);
        TextField.setText("Tic Tac Toe");
        TextField.setOpaque(true);

        Title_panel.setLayout(new BorderLayout());
        Title_panel.setBounds(0, 0, 800, 100);
        Title_panel.add(TextField);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(Color.WHITE);


        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            button_panel.add(buttons[i]);
            buttons[i].setFocusable(false);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 120));
            buttons[i].addActionListener(this);

        }
        frame.add(Title_panel, BorderLayout.NORTH);
        frame.add(button_panel, BorderLayout.CENTER);
//        this.startGame();
        firstTurn();
    }

    private void firstTurn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (random.nextInt(2) == 0) {
            player1Turn = true;
            TextField.setText("X Turn");
        } else {
            player1Turn = false;
            TextField.setText("O Turn");
        }
    }

    public void startGame() {
        try {
            // Send instructions to both players
            player1out.writeUTF("X Turn");
            player2out.writeUTF("O Turn");

            while (true) {
                if (player1Turn) {
                    player1Move();
                } else {
                    player2Move();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void player1Move() {
        try {
            String move = player1In.readUTF();
            int moveIndex = Integer.parseInt(move);
            buttons[moveIndex].setText("X");
            buttons[moveIndex].setForeground(Color.RED);
            check();
            player1Turn = false;
            player2Turn = true;
            player2out.writeUTF("Your Turn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void player2Move() {
        try {
            String move = player2In.readUTF();
            int moveIndex = Integer.parseInt(move);
            buttons[moveIndex].setText("O");
            buttons[moveIndex].setForeground(Color.BLUE);
            check();
            player1Turn = true;
            player2Turn = false;
            player1out.writeUTF("Your Turn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (buttons[i].getText() == "") {  // Ensure the button is empty before proceeding
                    if (player1Turn) {
                        buttons[i].setForeground(Color.RED);
                        buttons[i].setText("X");
                        player1Turn = false;
                        player2Turn = true;
                        check();
                        TextField.setText("O Turn");
                    } else { // Player 2's (O) turn
                        buttons[i].setForeground(Color.BLUE);
                        buttons[i].setText("O");
                        player2Turn = false;
                        player1Turn = true;
                        check();
                        TextField.setText("X Turn");
                    }
                }
            }
        }
    }




    public void check() {
        if ((buttons[0].getText() == "X" && buttons[1].getText() == "X" && buttons[2].getText() == "X")) {
            xWins(0, 1, 2);
        }
        if ((buttons[3].getText() == "X") && (buttons[4].getText() == "X") && (buttons[5].getText() == "X")) {
            xWins(3, 4, 5);
        }
        if ((buttons[6].getText() == "X") && (buttons[7].getText() == "X") && (buttons[8].getText() == "X")) {
            xWins(6, 7, 8);
        }
        if ((buttons[0].getText() == "X") && (buttons[3].getText() == "X") && (buttons[6].getText() == "X")) {
            xWins(0, 3, 6);
        }
        if ((buttons[1].getText() == "X") && (buttons[4].getText() == "X") && (buttons[7].getText() == "X")) {
            xWins(1, 4, 7);
        }
        if ((buttons[2].getText() == "X") && (buttons[5].getText() == "X") && (buttons[8].getText() == "X")) {
            xWins(2, 5, 8);
        }
        if ((buttons[0].getText() == "X") && (buttons[4].getText() == "X") && (buttons[8].getText() == "X")) {
            xWins(0, 4, 8);
        }
        if ((buttons[2].getText() == "X") && (buttons[4].getText() == "X") && (buttons[6].getText() == "X")) {
            xWins(2, 4, 6);
        }
        if ((buttons[0].getText() == "O" && buttons[1].getText() == "O" && buttons[2].getText() == "O")) {

            oWins(0, 1, 2);
        }
        if ((buttons[3].getText() == "O") && (buttons[4].getText() == "O") && (buttons[5].getText() == "O")) {
            oWins(3, 4, 5);
        }
        if ((buttons[6].getText() == "O") && (buttons[7].getText() == "O") && (buttons[8].getText() == "O")) {
            oWins(6, 7, 8);
        }
        if ((buttons[0].getText() == "O") && (buttons[3].getText() == "O") && (buttons[6].getText() == "O")) {
            oWins(0, 3, 6);
        }
        if ((buttons[1].getText() == "O") && (buttons[4].getText() == "O") && (buttons[7].getText() == "O")) {
            oWins(1, 4, 7);
        }
        if ((buttons[2].getText() == "O") && (buttons[5].getText() == "O") && (buttons[8].getText() == "O")) {
            oWins(2, 5, 8);
        }
        if ((buttons[0].getText() == "O") && (buttons[4].getText() == "O") && (buttons[8].getText() == "O")) {
            xWins(0, 4, 8);
        }
        if ((buttons[2].getText() == "O") && (buttons[4].getText() == "O") && (buttons[6].getText() == "O")) {
            xWins(2, 4, 6);
        }
    }

    private void oWins(int a, int b, int c) {
        buttons[a].setBackground(Color.YELLOW);
        buttons[b].setBackground(Color.YELLOW);
        buttons[c].setBackground(Color.YELLOW);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        TextField.setText("O Wins!");
    }


    private void xWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        TextField.setText("X Wins!");
    }

}