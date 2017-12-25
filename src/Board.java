

public class Board{
    // This  class is responsible for modeling the state of the board for each player

    private Box[][] board;

    public Board()  {
        board = new Box[10][10];

        for (int r = 1; r < 11; r++) {
            // add(new JLabel(Integer.toString(r), SwingConstants.CENTER));
            for (int c = 1; c < 11; c++) {
                board[r - 1][c - 1] = new Box(false);
            }
        }
    }

    /* The following methods all affect the inner state of the board and
    involve the logic of the game.
     */

    // In the following method, we make the functionality to place a
    // ship somewhere on the board.
    // @param s- The ship to be placed
    // @param row- the row where the head of the ship will go
    // @param col- the column where the head of the ship will go

    public void placeShip(Ship s, int row, int col) {
        // We know that the ship is either horizontal or not
        boolean isHorizontal = s.isHorizontal();
        int size = s.size();
        if (!(row < 0 || col < 0 || row >= 10 ||  col >= 10)) {
            if (isHorizontal) {
                if (col + size - 1 <= 10) {
                    for(int c = col; c < col + size; c++) {
                        Box b = board[row][c];
                        b.changeState(true, s);
                    }
                }
            } else {
                for(int r = row; r < row + size; r++) {
                    Box b = board[r][col];
                    b.changeState(true, s);
                }
            }
            s.setLocation(row, col);
        }
    }

    public boolean goodPlacement(Ship s, int row, int col) {
        int length = s.size();
        boolean answer = true;
        if (s.getOrientation()) {
            for (int i = col; i < col + length; i++) {
                answer = answer && i >= 0 && i < 10 && !(board[row][i].hasShip());
            }
        } else {
            for (int i = row; i < row + length; i++) {
                answer = answer && i >= 0 && i < 10 && !(board[i][col].hasShip());
            }
        }
        return answer;
    }

    public boolean hasShip(int row, int col) {
        if (row >= 0 && row < 10 && col >= 0 && col < 10) {
            Box b = board[row][col];
            return b.hasShip();
        }
        return false;
    }

    public boolean isGuessed(int row, int col) {
        Box b = board[row][col];
        return b.isGuessed();
    }

    public boolean isHit(int row, int col) {
        return board[row][col].isHit();
    }

    public void markHit(int row, int col) {
        if (row >= 0 && row < 10 && col >= 0 && col < 10) {
            Box b = board[row][col];
            b.markHit();
        }
    }

    public void markMiss(int row, int col) {
        if (row >= 0 && row < 10 && col >= 0 && col < 10) {
            Box b = board[row][col];
            b.markMiss();
        }
    }

}
