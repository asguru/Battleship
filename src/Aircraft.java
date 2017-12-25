public class Aircraft extends Ship {

    public Aircraft() {
        super(5);
    }

    @Override
    public void fire(int row, int col, Board b) {
        for (int i = Math.max(0, row - 1); i < Math.min(10, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j < Math.min(10, col + 1); j++) {
                if (b.hasShip(i, j)) {
                    b.markHit(i, j);
                } else {
                    b.markMiss(i, j);
                }
            }
        }
    }
}
