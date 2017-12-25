public class Ship {

    private final int size;

    private int hitsLeft;

    // The default orientation is horizontal
    private boolean horizontal;

    // The following are default values for the location of the ship.
    // This is meant to be an impossible value so that the program can check
    // if the ship has been placed

    private int r = -1;

    private int c = -1;

    public Ship(int l) {
        size = l;
        hitsLeft = size;
        horizontal = true;
    }

    public int size() {
        return size;
    }

    public boolean getOrientation() {
        return horizontal;
    }

    public void setOrientation(boolean h) {
        horizontal = h;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isPlaced() {
        return !(r == -1 || c  == -1);
    }

    public void setLocation(int row, int col) {
        r = row;
        c = col;
    }

    public int getRow() {
        return r;
    }

    public int getCol() {
        return c;
    }

    public void addHit() {
        hitsLeft--;
    }

    public int getHitsLeft() {
        return hitsLeft;
    }

    public boolean isSunk() {
        return hitsLeft == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ship) {
            Ship other = (Ship) obj;
            boolean answer = true;
            answer = answer && r == other.getRow();
            answer = answer && c == other.getCol();
            answer = answer && horizontal == other.getOrientation();
            answer = answer && size == other.size();
            return answer;
        }
        return false;
    }

    public void fire(int row, int col, Board b) {
        if (b.hasShip(row, col)) {
            b.markHit(row, col);
        } else {
            b.markMiss(row, col);
        }
    }

}
