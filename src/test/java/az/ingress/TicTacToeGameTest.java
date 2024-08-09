package az.ingress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TicTacToeGameTest {

    private TicTacToeGame game;

    @BeforeEach
    public void setUp() {
        game = new TicTacToeGame();
    }

    @Test
    public void whenXOutsideBoardThenRuntimeException() {
//        assertThrows(RuntimeException.class, () -> game.play(2, 5));
        assertThatThrownBy(() -> game.play(5, 2))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("x out of bounds");
    }

    @Test
    public void whenYOutsideBoardThenRuntimeException() {
        assertThatThrownBy(() -> game.play(3, 6))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("y out of bounds");

    }

    @Test
    public void whenOccupiedBoardThenRuntimeException() {
        game.play(2, 3);
        assertThatThrownBy(() -> game.play(2, 3))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Box is occupied");
    }

    @Test
    public void givenWhenFirstTurnThenNextPlayerX() {
        assertEquals('X', game.nextPlayer());
    }

    @Test
    public void givenLastTurnWasXWhenNextPlayerThenO() {
        game.play(2, 3);
        assertEquals('O', game.nextPlayer());
    }

    @Test
    public void whenPlayThenNoWinner() {
        String actual = game.play(2, 3);
        assertEquals("No winner", actual);
    }

    @Test
    public void whenPlayAndWholeHorizontalLineThenWinner() {
        game.play(1, 1); //X
        game.play(1, 2); //O
        game.play(2, 1); //X
        game.play(1, 3); //O
        String actual = game.play(3, 1); //X
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() {
        game.play(2, 1); // X
        game.play(1, 1); // O
        game.play(3, 1); // X
        game.play(1, 2); // O
        game.play(2, 2); // X
        String actual = game.play(1, 3); // O
        assertEquals("O is the winner", actual);
    }

    @Test
    public void whenPlayAndTopBottomDiagonalLineThenWinner() {
        game.play(1, 1); // X
        game.play(1, 3); // O
        game.play(2, 2); // X
        game.play(1, 2); // O
        String actual = game.play(3, 3); // X
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenAllBoxesAreFilledThenDraw() {
        game.play(1, 1);
        game.play(1, 2);
        game.play(1, 3);
        game.play(2, 1);
        game.play(2, 3);
        game.play(2, 2);
        game.play(3, 1);
        game.play(3, 3);
        String actual = game.play(3, 2);
        assertEquals("The result is draw", actual);
    }

}