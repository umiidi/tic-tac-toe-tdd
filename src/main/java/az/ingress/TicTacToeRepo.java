package az.ingress;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TicTacToeRepo {

    private final List<TicTacToe> boardState = new ArrayList<>();

    void saveMove(TicTacToe ticTacToe) {
        ticTacToe.setId(boardState.size());
        boardState.add(ticTacToe);
    }

    List<TicTacToe> getBoardState() {
        return new LinkedList<>(boardState); // immutable
    }

    TicTacToe getLastMove() {
        if (boardState.isEmpty()) {
            return null;
        }
        return boardState.get(boardState.size() - 1);
    }

}
