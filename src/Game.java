/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        Board board1 = new Board();

        Board board2 = new Board();

        Player p1 = new Player(board1, board2);

        Player p2 = new Player(board2, board1);

        final JFrame frame = new JFrame("Battleship");
        frame.setVisible(true);
        frame.setSize(1000, 600);
        frame.setLocation(100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.decode("#00FFFF"));

        JOptionPane pane = new JOptionPane();

        GameCourt court = new GameCourt(p1, p2);

        cp.add(court, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(court, "<html><b>Instructions:</b></html> \n" +
                "This game is Battleship, and all basic rules apply. First, \n" +
                "each player will place their ships. \n" +
                "Next, each player will take turns guessing the location of each ship, \n" +
                "and the person to guess the other player's ships first wins. \n" +
                "To guess a ship location, just click on one of the squares in the grid, \n" +
                "and make sure you are clicking in your own grid (although the game will just \n" +
                "wait until you click on a valid square in your own grid). \n" +
                "While guessing the location of your opponent's ship, just click \n" +
                "the desired location on the board. \n" +
                "This game has a twist; you can choose which ship to fire from, and \n" +
                "firing from your aircraft carrier will allow you to hit a 2x2 box. However, \n" +
                "in doing this you skip your next two turns, so be careful. Also, if your \n" +
                "aircraft carrier is sunk, you will no longer have this option. \n" +
                "The player who sinks the other's ships first wins. " +
                "Have fun!");


        court.placeP1Ships();

        Timer playGame = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.changeMode();
                court.repaint();
                /*  while (!(p1.hasLost() || p2.hasLost())) {
                    court.p1TurnTaking();
                    if (!p2.hasLost()) {
                        court.p2TurnTaking();
                    }
                } */

                court.playGame();

                if (p2.hasLost()) {
                    JOptionPane.showMessageDialog(court, "P1 Wins!");
                } else if (p1.hasLost()) {
                    JOptionPane.showMessageDialog(court, "P2 Wins!");
                }

            }
        });

        Timer playerTwo = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.placeP2Ships();
                playGame.setRepeats(false);
                playGame.start();
            }
        });
        playerTwo.setRepeats(false);
        playerTwo.start();



    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}
