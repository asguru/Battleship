import java.awt.*;
import javax.swing.*;

public class Box {
    // This class represents a single box in the Battleship grid.
    // It either has or doesn't have a ship in it

    private boolean existShip = false;

    // If someone guesses this box, this will become true (i.e.
    // someone has attempted to hit it)
    private boolean attemptedHit = false;

    // Marks whether this box is a hit
    private boolean isHit = false;

    private Ship associatedShip;

    public Box(boolean s) {
        existShip = s;
    }

    public void changeState(boolean es, Ship s) {
        existShip = es;
        associatedShip = s;
    }

    public boolean hasShip() {
        return existShip;
    }

    public boolean isGuessed() {
        return attemptedHit;
    }

    public boolean isHit() {
        return isHit;
    }

    public void markHit() {
        if (existShip) {
            attemptedHit = true;
            isHit = true;
            associatedShip.addHit();
        }
    }

    public void markMiss() {
        attemptedHit = true;
    }
}
