import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameCourt extends JComponent{

    private Player p1;
    private Player p2;

    private boolean shipPlaceMode = true;

    private boolean p1Turn = false;


    public GameCourt(Player playerOne, Player playerTwo) {
        p1 = playerOne;
        p2 = playerTwo;


    }

    public void changeMode() {
        shipPlaceMode  = false;
    }

    public void playGame() {
        addMouseListener(new MouseAdapter() {
            boolean playerOneTurn = true;

            int p1TurnsLeft = 0;
            int p2TurnsLeft = 0;

            public void mouseClicked(MouseEvent e) {
                if (!(p1.hasLost() || p2.hasLost())) {
                    int x = e.getX();
                    int y = e.getY();

                    if (p1TurnsLeft > p2TurnsLeft && p2TurnsLeft > 0) {
                        p1TurnsLeft -= p2TurnsLeft;
                        p2TurnsLeft = 0;
                    } else if (p2TurnsLeft > p1TurnsLeft && p1TurnsLeft > 0) {
                        p2TurnsLeft -= p1TurnsLeft;
                        p1TurnsLeft = 0;
                    }

                    int ac = -1;


                    Ship s1 = p1.getAircraft();

                    Ship s2 = p1.getNonAircraft();

                    Ship s3 = p2.getAircraft();

                    Ship s4 = p2.getNonAircraft();

                    if (playerOneTurn && p1TurnsLeft == 0) {
                        x = (x - 50) / 40;
                        y = (y - 30) / 40;

                        if (p1.hasAircraft()) {

                            while (ac != 1 && ac != 0) {
                                String input = JOptionPane.
                                        showInputDialog("(Player 1) Fire from Aircraft Carrier? (1 for Yes, 0 for No)");
                                try {
                                    ac = Integer.parseInt(input);
                                } catch (NumberFormatException z) {
                                    ac = -1;
                                }
                            }
                        }

                        // y becomes the row, x becomes the column

                        if (y >= 0 && y < 10 && x >= 0 && x < 10 && !p1.hasGuessed(y, x)) {
                            if (ac == 1) {
                                s1.fire(y, x, p1.getOppBoard());
                                p1TurnsLeft = 2;
                            } else {
                                s2.fire(y, x, p1.getOppBoard());
                            }
                            repaint();

                            int prev = p2.getNumShips();

                            p2.refreshShips();

                            int after = p2.getNumShips();

                            if (prev > after) {
                                JOptionPane.showMessageDialog(GameCourt.this, "Sunk P2's ship!");
                            }

                            if (p2TurnsLeft == 0) {
                                playerOneTurn = !playerOneTurn;
                                JOptionPane.showMessageDialog(GameCourt.this,
                                        "It's Player 2's Turn!");
                            } else if (p2TurnsLeft > 0) {
                                if (p2TurnsLeft == p1TurnsLeft) {
                                    p1TurnsLeft = 0;
                                    p2TurnsLeft = 0;
                                    playerOneTurn = !playerOneTurn;
                                    JOptionPane.showMessageDialog(GameCourt.this,
                                            "It's Player 2's Turn!");
                                } else {
                                    p2TurnsLeft--;
                                }
                            }

                        }
                    } else if (!playerOneTurn && p2TurnsLeft == 0) {
                        x = (x - 550) / 40;
                        y = (y - 30) / 40;

                        if (p2.hasAircraft()) {

                            while (ac != 1 && ac != 0) {
                                String input = JOptionPane.showInputDialog("(Player 2) Fire from Aircraft Carrier? " +
                                        "(1 for Yes, 0 for No)");
                                try {
                                    ac = Integer.parseInt(input);
                                } catch (NumberFormatException z) {
                                    ac = -1;
                                }
                            }
                        }

                        if (y >= 0 && y < 10 && x >= 0 && x < 10 && !p2.hasGuessed(y, x)) {

                            if (ac == 1) {
                                s3.fire(y, x, p2.getOppBoard());
                                p2TurnsLeft = 2;
                            } else {
                                s4.fire(y, x, p2.getOppBoard());
                            }
                            repaint();

                            int prev = p1.getNumShips();

                            p1.refreshShips();

                            int after = p1.getNumShips();

                            if (prev > after) {
                                JOptionPane.showMessageDialog(GameCourt.this, "Sunk P1's ship!");
                            }

                            if (p1TurnsLeft == 0) {
                                playerOneTurn = !playerOneTurn;
                                JOptionPane.showMessageDialog(GameCourt.this,
                                        "It's Player 1's Turn!");
                            } else if (p1TurnsLeft > 0) {
                                if (p1TurnsLeft == p2TurnsLeft) {
                                    p1TurnsLeft = 0;
                                    p2TurnsLeft = 0;
                                    playerOneTurn = !playerOneTurn;
                                } else {
                                    p1TurnsLeft--;
                                }
                            }
                        }
                    }

                    if (p1.hasLost()) {
                        JOptionPane.showMessageDialog(GameCourt.this,
                                "P2 Wins!");
                    } else if (p2.hasLost()) {
                        JOptionPane.showMessageDialog(GameCourt.this,
                                "P1 Wins!");
                    }

                } else {
                    if (p1.hasLost()) {
                        JOptionPane.showMessageDialog(GameCourt.this,
                                "P2 Wins!");
                    } else {
                        JOptionPane.showMessageDialog(GameCourt.this,
                                "P1 Wins!");
                    }
                }
            }
        });
    }


    public void placeP1Ships() {
        p1Turn = !p1Turn;

        repaint();

        Ship sub = new Ship(2);
        setShipOrientation(sub);
        int[] location = setShipLocation(sub, p1);
        p1.placeShip(sub, location[0], location[1]);
        repaint();

        Ship aircraft = new Aircraft();
        setShipOrientation(aircraft);
        location = setShipLocation(aircraft, p1);
        p1.placeShip(aircraft, location[0], location[1]);
        repaint();

        Ship normalShip = new Ship(3);
        setShipOrientation(normalShip);
        location = setShipLocation(normalShip, p1);
        p1.placeShip(normalShip, location[0], location[1]);
        repaint();

        Ship normalShip2 = new Ship(3);
        setShipOrientation(normalShip2);
        location = setShipLocation(normalShip2, p1);
        p1.placeShip(normalShip2, location[0], location[1]);
        repaint();

        Ship lengthFour = new Ship(4);
        setShipOrientation(lengthFour);
        location = setShipLocation(lengthFour, p1);
        p1.placeShip(lengthFour, location[0], location[1]);
        repaint();
    }

    public void placeP2Ships() {
        p1Turn = !p1Turn;

        repaint();

        Ship sub2 = new Ship(2);
        setShipOrientation(sub2);
        int[] location = setShipLocation(sub2, p2);
        p2.placeShip(sub2, location[0], location[1]);
        repaint();

        Ship aircraft2 = new Aircraft();
        setShipOrientation(aircraft2);
        location = setShipLocation(aircraft2,  p2);
        p2.placeShip(aircraft2, location[0], location[1]);
        repaint();

        Ship normalShip3 = new Ship(3);
        setShipOrientation(normalShip3);
        location = setShipLocation(normalShip3, p2);
        p2.placeShip(normalShip3, location[0], location[1]);
        repaint();

        Ship normalShip4 = new Ship(3);
        setShipOrientation(normalShip4);
        location = setShipLocation(normalShip4, p2);
        p2.placeShip(normalShip4, location[0], location[1]);
        repaint();

        Ship lengthFour2 = new Ship(4);
        setShipOrientation(lengthFour2);
        location = setShipLocation(lengthFour2, p2);
        p2.placeShip(lengthFour2, location[0], location[1]);
        repaint();

    }

    public void setShipOrientation(Ship s) {
        JOptionPane pane = new JOptionPane();
        int orientation = -1;
        String input;

        while (orientation != 0 && orientation != 1) {

            if (s instanceof Aircraft) {
                input = pane.showInputDialog("What orientation would you like for your Aircraft Carrier? " +
                        "(0 for horizontal, 1 for vertical)");
            } else {
                input = pane.showInputDialog(this,
                        "What orientation would you like for this ship (length = " + s.size() + ")? " +
                                "(0 for horizontal, 1 for vertical)");
            }
            try {
                orientation = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                orientation = -1;
            }
        }
        // In ship and all subclasses, true is horizontal and false is vertical
        // In the prompt, 0 is horizontal and 1 is vertical, so orientation == 0
        // makes this boolean for the input

        s.setOrientation(orientation == 0);

    }

    public int[] setShipLocation(Ship s, Player p) {
        JOptionPane pane = new JOptionPane();
        int r = -1;

        int c = -1;

        String input;
        String input2;

        while (!(p.goodPlacement(s, r, c))) {
            if (s instanceof Aircraft) {
                input = pane.showInputDialog(this,
                        "What will the row of the Aircraft Carrier be?");
            } else {
                input = pane.showInputDialog(this,
                        "What will the row of this ship (length = " + s.size() + ") be?");
            }
            try {
                r = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                r = -1;
            }

            if (s instanceof Aircraft) {
                input2 = pane.showInputDialog(this,
                        "What will the column of the Aircraft Carrier be?");
            } else {
                input2 = pane.showInputDialog(this,
                        "What will the column of the ship (length = " + s.size() + ") be?");
            }
            try {
                c = Integer.parseInt(input2) - 1;
            } catch (NumberFormatException e) {
                c = -1;
            }

        }

        return new int[]{r, c};
    }

    public void paintComponent(Graphics g) {
        if (shipPlaceMode) {
            if (p1Turn) {
                g.drawString("1", 280, 55);
                g.drawString("2", 280, 95);
                g.drawString("3", 280, 135);
                g.drawString("4", 280, 175);
                g.drawString("5", 280, 215);
                g.drawString("6", 280, 255);
                g.drawString("7", 280, 295);
                g.drawString("8", 280, 335);
                g.drawString("9", 280, 375);
                g.drawString("10", 280, 415);

                g.drawString("1", 315, 25);
                g.drawString("2", 355, 25);
                g.drawString("3", 395, 25);
                g.drawString("4", 435, 25);
                g.drawString("5", 475, 25);
                g.drawString("6", 515, 25);
                g.drawString("7", 555, 25);
                g.drawString("8", 595, 25);
                g.drawString("9", 635, 25);
                g.drawString("10",675, 25);

                for (int r = 0; r < 10; r++) {
                    for (int c = 0; c < 10; c++) {
                        if (p1.hasShip(r, c)) {
                            g.setColor(Color.GREEN);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(40 * c + 300, 40 * r + 30, 40, 40);
                        g.setColor(Color.BLACK);
                        g.drawRect(40 * c + 300, 40 * r + 30, 40, 40);
                    }
                }

            } else {

                g.drawString("1", 280, 55);
                g.drawString("2", 280, 95);
                g.drawString("3", 280, 135);
                g.drawString("4", 280, 175);
                g.drawString("5", 280, 215);
                g.drawString("6", 280, 255);
                g.drawString("7", 280, 295);
                g.drawString("8", 280, 335);
                g.drawString("9", 280, 375);
                g.drawString("10", 280, 415);

                g.drawString("1", 315, 25);
                g.drawString("2", 355, 25);
                g.drawString("3", 395, 25);
                g.drawString("4", 435, 25);
                g.drawString("5", 475, 25);
                g.drawString("6", 515, 25);
                g.drawString("7", 555, 25);
                g.drawString("8", 595, 25);
                g.drawString("9", 635, 25);
                g.drawString("10",675, 25);

                for (int r = 0; r < 10; r++) {
                    for (int c = 0; c < 10; c++) {
                        if (p2.hasShip(r, c)) {
                            g.setColor(Color.GREEN);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(40 * c + 300, 40 * r + 30, 40, 40);
                        g.setColor(Color.BLACK);
                        g.drawRect(40 * c + 300, 40 * r + 30, 40, 40);
                    }
                }

            }
        } else {

            g.drawString("1", 30, 55);
            g.drawString("2", 30, 95);
            g.drawString("3", 30, 135);
            g.drawString("4", 30, 175);
            g.drawString("5", 30, 215);
            g.drawString("6", 30, 255);
            g.drawString("7", 30, 295);
            g.drawString("8", 30, 335);
            g.drawString("9", 30, 375);
            g.drawString("10", 30, 415);

            g.drawString("1", 65, 25);
            g.drawString("2", 105, 25);
            g.drawString("3", 145, 25);
            g.drawString("4", 185, 25);
            g.drawString("5", 225, 25);
            g.drawString("6", 265, 25);
            g.drawString("7", 305, 25);
            g.drawString("8", 345, 25);
            g.drawString("9", 385, 25);
            g.drawString("10",425, 25);

            g.drawString("Player 1 Targets", 200, 450);

            for (int r = 0; r < 10; r++) {
                for (int c = 0; c < 10; c++) {
                    if (p1.hasGuessed(r, c)) {
                        if (p1.hasHit(r, c)) {
                            g.setColor(Color.RED);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(40 * c + 50, 40 * r + 30, 40, 40);
                    }
                    g.setColor(Color.BLACK);
                    g.drawRect(40 * c + 50, 40 * r + 30, 40, 40);
                }
            }

            g.drawString("1", 530, 55);
            g.drawString("2", 530, 95);
            g.drawString("3", 530, 135);
            g.drawString("4", 530, 175);
            g.drawString("5", 530, 215);
            g.drawString("6", 530, 255);
            g.drawString("7", 530, 295);
            g.drawString("8", 530, 335);
            g.drawString("9", 530, 375);
            g.drawString("10", 530, 415);

            g.drawString("1", 565, 25);
            g.drawString("2", 605, 25);
            g.drawString("3", 645, 25);
            g.drawString("4", 685, 25);
            g.drawString("5", 725, 25);
            g.drawString("6", 765, 25);
            g.drawString("7", 805, 25);
            g.drawString("8", 845, 25);
            g.drawString("9", 885, 25);
            g.drawString("10",925, 25);

            g.drawString("Player 2 Targets", 700, 450);

            for (int r = 0; r < 10; r++) {
                for (int c = 0; c < 10; c++) {
                    if (p2.hasGuessed(r, c)) {
                        if (p2.hasHit(r, c)) {
                            g.setColor(Color.RED);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(40 * c + 550, 40 * r + 30, 40, 40);
                    }
                    g.setColor(Color.BLACK);
                    g.drawRect(40 * c + 550, 40 * r + 30, 40, 40);
                }
            }

        }
    }


}
