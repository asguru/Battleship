import java.util.*;

public class Player {

    // This class represents a player in the game

    private Board yourFleet;

    private Board otherFleet;

    private List<Ship> ships;

    private List<String> moves;

    private boolean hasFired = false;

    public enum mode {
        ShipPlaceMode,
        GuessMode
    }

    public Player(Board self, Board enemy) {
        yourFleet = self;
        otherFleet = enemy;

        ships = new ArrayList<Ship>();

    }

    public Board getOppBoard() {
        return otherFleet;
    }

    public void placeShip(Ship s, int r, int c) {
        yourFleet.placeShip(s, r, c);
        ships.add(s);

    }

    public boolean hasShip(int r, int c) {
        return yourFleet.hasShip(r, c);
    }

    public boolean goodPlacement(Ship s, int r, int c) {
        if (r >= 0 && c >= 0 && r < 10 && c < 10) {
            return yourFleet.goodPlacement(s, r, c);
        }
        return false;
    }

    public void refreshShips() {
        Iterator<Ship> itr = ships.iterator();
        while (itr.hasNext()) {
            Ship s = itr.next();
            if (s.isSunk()) {
                itr.remove();
            }
        }
    }

    public boolean hasAircraft() {
        boolean result = false;
        for (Ship s : ships) {
            result = result || (s instanceof Aircraft);
        }
        return result;
    }

    public Ship getAircraft() {
        for (Ship s : ships) {
            if (s instanceof Aircraft) {
                return s;
            }
        }
        return ships.get(0);
    }

    public Ship getNonAircraft() {
        for (Ship s : ships) {
            if (!(s instanceof Aircraft)) {
                return s;
            }
        }
        return ships.get(0);
    }

    public int getNumShips() {
        return ships.size();
    }

    // This method is used for debugging purposes in the test cases
    public void guessLocation(int r, int c) {
        boolean existShip = otherFleet.hasShip(r, c);
        if (existShip) {
            otherFleet.markHit(r, c);
        } else {
            otherFleet.markMiss(r, c);
        }
        hasFired = true;
    }

    public boolean hasGuessed(int r, int c) {
        if (r >= 0 && r < 10 && c >= 0 && c < 10) {
            return otherFleet.isGuessed(r, c);
        }
        return true;
    }

    public boolean hasHit(int r, int c) {
        return otherFleet.isHit(r, c);
    }


    public boolean hasLost() {
        return ships.isEmpty();
    }

}
