package az.ingress;

public class TicTacToeGame {

    private final Character[][] board = {
            {'\0', '\0', '\0', '\0'},
            {'\0', '\0', '\0', '\0'},
            {'\0', '\0', '\0', '\0'}
    };

    private Character lastPlayer = '\0';

    public String play(int x, int y) {
        checkXAxis(x);
        checkYAxis(y);
        lastPlayer = nextPlayer();
        setBox(x, y);
        if (isWin()) {
            return String.format("%s is the winner", lastPlayer);
        } else if (isDraw()) {
            return "The result is draw";
        }
        return "No winner";
    }

    public char nextPlayer() {
        if (lastPlayer == 'X') {
            return 'O';
        }
        return 'X';
    }

    private void setBox(int x, int y) {
        if (board[x - 1][y - 1] != '\0') {
            throw new RuntimeException("Box is occupied");
        }
        board[x - 1][y - 1] = lastPlayer;
    }

    private void checkXAxis(int x) {
        if (x < 1 || x > 3) {
            throw new RuntimeException("x out of bounds");
        }
    }

    private void checkYAxis(int y) {
        if (y < 1 || y > 3) {
            throw new RuntimeException("y out of bounds");
        }
    }

    private boolean isWin() {
        int playerTotal = lastPlayer * 3;
        return checkDiagonals(playerTotal) || checkHorizontalAndVerticalWin(playerTotal);
    }

    private boolean isDraw() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkHorizontalAndVerticalWin(int playerTotal) {
        for (int i = 0; i < 3; i++) {
            int horizontal = board[0][i] + board[1][i] + board[2][i];
            int vertical = board[i][0] + board[i][1] + board[i][2];
            if (horizontal == playerTotal || vertical == playerTotal) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(int playerTotal) {
        int mainDiagonal = board[0][0] + board[1][1] + board[2][2];
        int antiDiagonal = board[0][2] + board[2][2] + board[2][1];
        return mainDiagonal == playerTotal || antiDiagonal == playerTotal;
    }

}
