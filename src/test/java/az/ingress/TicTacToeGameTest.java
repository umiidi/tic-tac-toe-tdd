package az.ingress;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicTacToeGameTest {

    @Mock
    private TicTacToeRepo ticTacToeRepo;

    @InjectMocks
    private TicTacToeGame game;

    @Test
    public void whenXOutsideBoardThenRuntimeException() {
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
        // arrange
        when(ticTacToeRepo.getBoardState()).thenReturn(List.of(new TicTacToe(2, 3, 'X')));

        // assert
        assertThatThrownBy(() -> game.play(2, 3))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Box is occupied");

        verify(ticTacToeRepo, times(1)).getBoardState();
    }

    @Test
    public void givenWhenFirstTurnThenNextPlayerX() {
        //arrange
        when(ticTacToeRepo.getLastMove()).thenReturn(null);

        //assert
        assertEquals('X', game.nextPlayer());

        verify(ticTacToeRepo, times(1)).getLastMove();
    }

    @Test
    public void givenLastTurnWasXWhenNextPlayerThenO() {
        //arrange
        when(ticTacToeRepo.getLastMove()).thenReturn(new TicTacToe(2, 3, 'X'));

        //assert
        assertEquals('O', game.nextPlayer());

        verify(ticTacToeRepo, times(1)).getLastMove();
    }

    @Test
    public void whenPlayThenNoWinner() {
        //arrange
        when(ticTacToeRepo.getLastMove()).thenReturn(new TicTacToe(2, 3, 'X'));

        //actual
        String actual = game.play(2, 3);

        //assert
        assertEquals("No winner", actual);

        verify(ticTacToeRepo, times(1)).getLastMove();
    }

    @Test
    public void whenPlayAndWholeHorizontalLineThenWinner() {
        TicTacToe t1 = new TicTacToe(1, 1, 'X');
        TicTacToe t2 = new TicTacToe(1, 2, 'O');
        TicTacToe t3 = new TicTacToe(2, 1, 'X');
        TicTacToe t4 = new TicTacToe(1, 3, 'O');

        // arrange
        when(ticTacToeRepo.getBoardState())
                .thenReturn(List.of(t1, t2, t3, t4))
                .thenReturn(List.of(t1, t2, t3, t4, new TicTacToe(3, 1, 'X')));

        when(ticTacToeRepo.getLastMove()).thenReturn(t4);

        //actual
        String actual = game.play(3, 1); //X

        //assert
        assertEquals("X is the winner", actual);

        verify(ticTacToeRepo, times(2)).getBoardState();
        verify(ticTacToeRepo, times(1)).getLastMove();
    }

    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() {
        TicTacToe t1 = new TicTacToe(2, 1, 'X');
        TicTacToe t2 = new TicTacToe(1, 1, 'O');
        TicTacToe t3 = new TicTacToe(3, 1, 'X');
        TicTacToe t4 = new TicTacToe(1, 2, 'O');
        TicTacToe t5 = new TicTacToe(2, 2, 'X');

        // arrange
        when(ticTacToeRepo.getBoardState())
                .thenReturn(List.of(t1, t2, t3, t4, t5))
                .thenReturn(List.of(t1, t2, t3, t4, t5, new TicTacToe(1, 3, 'O')));

        when(ticTacToeRepo.getLastMove()).thenReturn(t5);

        //actual
        String actual = game.play(1, 3); // O

        //assert
        assertEquals("O is the winner", actual);

        verify(ticTacToeRepo, times(2)).getBoardState();
        verify(ticTacToeRepo, times(1)).getLastMove();

    }

    @Test
    public void whenPlayAndTopBottomDiagonalLineThenWinner() {
        TicTacToe t1 = new TicTacToe(1, 1, 'X');
        TicTacToe t2 = new TicTacToe(1, 3, 'O');
        TicTacToe t3 = new TicTacToe(2, 2, 'X');
        TicTacToe t4 = new TicTacToe(1, 2, 'O');

        // arrange
        when(ticTacToeRepo.getBoardState())
                .thenReturn(List.of(t1, t2, t3, t4))
                .thenReturn(List.of(t1, t2, t3, t4, new TicTacToe(3, 3, 'X')));

        when(ticTacToeRepo.getLastMove()).thenReturn(t4);

        //actual
        String actual = game.play(3, 3); // X

        //assert
        assertEquals("X is the winner", actual);

        verify(ticTacToeRepo, times(2)).getBoardState();
        verify(ticTacToeRepo, times(1)).getLastMove();
    }

    @Test
    public void whenAllBoxesAreFilledThenDraw() {
        TicTacToe t1 = new TicTacToe(1, 1, 'X');
        TicTacToe t2 = new TicTacToe(1, 2, 'O');
        TicTacToe t3 = new TicTacToe(1, 3, 'X');
        TicTacToe t4 = new TicTacToe(2, 1, 'O');
        TicTacToe t5 = new TicTacToe(2, 3, 'X');
        TicTacToe t6 = new TicTacToe(2, 2, 'O');
        TicTacToe t7 = new TicTacToe(3, 1, 'X');
        TicTacToe t8 = new TicTacToe(3, 3, 'O');

        // arrange
        when(ticTacToeRepo.getBoardState())
                .thenReturn(List.of(t1, t2, t3, t4, t5, t6, t7, t8))
                .thenReturn(List.of(t1, t2, t3, t4, t5, t6, t7, t8, new TicTacToe(3, 2, 'X')));
        when(ticTacToeRepo.getLastMove()).thenReturn(t8);

        //actual
        String actual = game.play(3, 2); // X

        //assert
        assertEquals("The result is draw", actual);

        verify(ticTacToeRepo, times(2)).getBoardState();
        verify(ticTacToeRepo, times(1)).getLastMove();
    }

}