import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    
    Ship s = new Ship(3);
    @Test
    public void placeShipBoard() throws Exception {
        Board b = new Board();
        b.placeShip(s, 2, 3);
        assertEquals(true, b.hasShip(2, 3));
        assertEquals(true, b.hasShip(2, 4));
        assertEquals(true, b.hasShip(2, 5));
        assertEquals(false, b.hasShip(2, 2));
        assertEquals(false, b.hasShip(2, 6));
    }

    @Test
    public void goodPlacementBoard() throws Exception {
        Board b = new Board();
        assertEquals(true, b.goodPlacement(s, 2, 3));
        assertEquals(false, b.goodPlacement(s, 100, 200));
        assertEquals(false, b.goodPlacement(s, 10, 10));
        assertEquals(false, b.goodPlacement(s, 1, -1));
    }

    @Test
    public void hasShipBoard() throws Exception {
        Board b = new Board();
        b.placeShip(s, 2, 3);
        assertEquals(false, b.hasShip(11, 11));
        assertEquals(false, b.hasShip(-1, -1));
        assertEquals(true, b.hasShip(2, 3));
    }

    @Test
    public void isGuessedBoard() throws Exception {
        Board b = new Board();
        b.markMiss(5, 6);
        assertEquals(true, b.isGuessed(5, 6));
        assertEquals(false, b.isGuessed(5, 7));
    }

    @Test
    public void isHitBoard() throws Exception {
        Board b = new Board();
        b.placeShip(s, 5, 6);
        b.markHit(5, 6);
        assertEquals(true, b.isHit(5, 6));
        assertEquals(false, b.isHit(5, 7));
    }

    @Test
    public void markHitBoard() throws Exception {
        Board b = new Board();
        b.placeShip(s, 4, 7);
        b.markHit(4, 7);
        b.markHit(11, 12);
        assertEquals(true, b.isHit(4, 7));
        assertEquals(false, b.isHit(5, 6));
    }

    @Test
    public void markMissBoard() throws Exception {
        Board b = new Board();
        b.placeShip(s, 4, 7);
        b.markMiss(2, 3);
        assertEquals(false, b.isHit(2, 3));
    }
    
    Ship y = new Ship(4);

    @Test
    public void changeState() throws Exception {
        Box b = new Box(false);
        b.changeState(true, y);
        assertEquals(true, b.hasShip());
    }

    @Test
    public void hasShip() throws Exception {
        Box b = new Box(false);
        b.changeState(true, y);
        b.changeState(false, null);
        assertEquals(false, b.hasShip());
    }

    @Test
    public void isGuessed() throws Exception {
        Box b = new Box(false);
        b.markMiss();
        assertEquals(true, b.isGuessed());
    }

    @Test
    public void isHit() throws Exception {
        Box b = new Box(false);
        b.changeState(true, y);
        assertEquals(false, b.isGuessed());
        assertEquals(false, b.isHit());
    }

    @Test
    public void markHit() throws Exception {
        Box b = new Box(false);
        b.changeState(true, y);
        b.markHit();
        assertEquals(true, b.isGuessed());
        assertEquals(true, b.isHit());
    }

    @Test
    public void markMiss() throws Exception {
        Box b = new Box(false);
        b.markMiss();
        assertEquals(true, b.isGuessed());
        assertEquals(false, b.isHit());
    }

    Board b1 = new Board();
    Board b2 = new Board();
    Ship z = new Ship(3);

    @Test
    public void placeShip() throws Exception {
        Player p = new Player(b1, b2);
        p.placeShip(z, 2, 3);
        assertEquals(true, p.hasShip(2, 3));
    }

    @Test
    public void hasShipPlayer() throws Exception {
        Player p = new Player(b1, b2);
        p.placeShip(z, 2, 3);
        assertEquals(true, p.hasShip(2, 5));
    }

    @Test
    public void goodPlacement() throws Exception {
        Player p = new Player(b1, b2);
        assertEquals(false, p.goodPlacement(z, 10, 10));
    }

    @Test
    public void refreshShips() throws Exception {
        Player p1 = new Player(b1, b2);
        Player p2 = new Player(b2, b1);
        p1.placeShip(z, 2, 3);
        int before = p1.getNumShips();
        p2.guessLocation(2, 3);
        p2.guessLocation(2, 4);
        p2.guessLocation(2, 5);
        int after = p2.getNumShips();
        assertEquals(1, before - after);
    }

    @Test
    public void hasGuessed() throws Exception {
        Player p1 = new Player(b1, b2);
        Player p2 = new Player(b2, b1);
        p2.guessLocation(3, 7);
        assertEquals(true, p2.hasGuessed(3, 7));
    }

    @Test
    public void hasHit() throws Exception {
        Player p1 = new Player(b1, b2);
        Player p2 = new Player(b2, b1);
        p1.placeShip(s, 2, 3);
        p2.guessLocation(2, 3);
        assertEquals(true, p2.hasHit(2, 3));
    }

    @Test
    public void hasLost() throws Exception {
        Board self = new Board();
        Board enemy = new Board();
        Player p1 = new Player(self, enemy);
        Ship s = new Ship(5);
        p1.placeShip(z, 1, 1);
        assertEquals(false, p1.hasLost());
    }

    @Test
    public void hasLost2() throws Exception {
        Board self = new Board();
        Board enemy = new Board();
        Player p1 = new Player(self, enemy);
        Player p2 = new Player(enemy, self);
        Ship n = new Ship(1);
        p1.placeShip(n, 1, 1);
        p2.guessLocation(1, 1);
        p1.refreshShips();
        assertEquals(true, p1.hasLost());
    }

    @Test
    public void fire() throws Exception {
        Aircraft ac = new Aircraft();
        Board b = new Board();
        ac.fire(2, 3, b);
        assertEquals(true, b.isGuessed(2, 3));
        assertEquals(true, b.isGuessed(2, 2));
        assertEquals(true, b.isGuessed(1, 3));
        assertEquals(true, b.isGuessed(1, 2));
    }
    
    @Test
    public void size() throws Exception {
        Ship l = new Ship(5);
        assertEquals(5, l.size());
    }

    @Test
    public void getOrientation() throws Exception {
        Ship m = new Ship(5);
        assertEquals(true, m.getOrientation());
    }

    @Test
    public void setOrientation() throws Exception {
        Ship o = new Ship(5);
        o.setOrientation(false);
        assertEquals(false, o.getOrientation());
    }

    @Test
    public void isHorizontal() throws Exception {
        Ship a = new Ship(5);
        a.setOrientation(false);
        a.setOrientation(true);
        assertEquals(true, a.isHorizontal());
    }

    @Test
    public void isPlaced() throws Exception {
        Ship s = new Ship(5);
        s.setLocation(5, 4);
        assertEquals(true, s.isPlaced());
    }

    @Test
    public void setLocation() throws Exception {
        Ship s = new Ship(5);
        s.setLocation(5, 4);
        assertEquals(5, s.getRow());
        assertEquals(4, s.getCol());
    }

    @Test
    public void getRow() throws Exception {
        Ship s = new Ship(5);
        s.setLocation(3, 7);
        assertEquals(3, s.getRow());
    }

    @Test
    public void getCol() throws Exception {
        Ship s = new Ship(5);
        s.setLocation(3, 7);
        assertEquals(7, s.getCol());
    }

    @Test
    public void getHitsLeft() throws Exception {
        Ship s = new Ship(5);
        assertEquals(5, s.getHitsLeft());
    }

    @Test
    public void addHit() throws Exception {
        Ship s = new Ship(5);
        s.addHit();
        s.addHit();
        assertEquals(3, s.getHitsLeft());
    }

    @Test
    public void isSunk() throws Exception {
        Ship s = new Ship(5);
        s.addHit();
        s.addHit();
        s.addHit();
        s.addHit();
        s.addHit();
        assertEquals(true, s.isSunk());
    }

    @Test
    public void equals() throws Exception {
        Ship s1 = new Ship(5);
        Ship s2 = new Ship(5);
        assertEquals(true, s1.equals(s2));
    }

    @Test
    public void fireShip() throws Exception {
        Ship s1 = new Ship(5);
        Board b = new Board();
        s1.fire(2, 3, b);
        assertEquals(true, b.isGuessed(2, 3));
    }

}