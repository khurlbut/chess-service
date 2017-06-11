package model.piece;

import static model.board.Sugar.capture;
import static model.board.Sugar.eventList;
import static model.board.Sugar.move;
import static model.board.Sugar.play;
import static model.board.Sugar.put;
import static model.board.Sugar.square;
import static model.enums.Color.BLACK;
import static model.enums.Color.WHITE;
import static model.enums.Column.A;
import static model.enums.Column.B;
import static model.enums.Column.C;
import static model.enums.Column.D;
import static model.enums.Column.E;
import static model.enums.Column.F;
import static model.enums.Column.G;
import static model.enums.Column.H;
import static model.enums.Rank.PAWN;
import static model.enums.Rank.QUEEN;
import static model.enums.Row.R2;
import static model.enums.Row.R3;
import static model.enums.Row.R4;
import static model.enums.Row.R5;
import static model.enums.Row.R6;
import static model.enums.Row.R7;
import static model.piece.PieceFactory.newPiece;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.board.BoardSetter;
import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.Square;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PieceTest {

    private Square d_3 = square(D, R3);
    private Square d_4 = square(D, R4);
    private Square d_6 = square(D, R6);
    private Square c_2 = square(C, R2);
    private Square d_7 = square(D, R7);
    private Square e_2 = square(E, R2);
    private Square c_7 = square(C, R7);
    private Square e_7 = square(E, R7);
    private Square a_7 = square(A, R7);
    private Square g_7 = square(G, R7);
    private Square b_2 = square(B, R2);
    private Square d_2 = square(D, R2);
    private Square f_2 = square(F, R2);
    private Square c_3 = square(C, R3);
    private Square e_3 = square(E, R3);
    private Square a_4 = square(A, R4);
    private Square b_4 = square(B, R4);
    private Square c_4 = square(C, R4);
    private Square e_4 = square(E, R4);
    private Square f_4 = square(F, R4);
    private Square g_4 = square(G, R4);
    private Square h_4 = square(H, R4);
    private Square c_5 = square(C, R5);
    private Square b_6 = square(B, R6);
    private Square d_5 = square(D, R5);
    private Square e_5 = square(E, R5);
    private Square f_6 = square(F, R6);

    private Piece w_queen_d_3;
    private Piece w_queen_d_4;
    private Piece w_queen_d_6;
    private Piece b_queen_d_7;

    private ChessBoard chessBoard;

    @Before
    public void setUp() {
        w_queen_d_3 = newPiece(WHITE, QUEEN, d_3);
        w_queen_d_4 = newPiece(WHITE, QUEEN, d_4);
        w_queen_d_6 = newPiece(WHITE, QUEEN, d_6);
        b_queen_d_7 = newPiece(BLACK, QUEEN, d_7);

        chessBoard = new ChessBoard();

        List<GameEvent> events = eventList(put(w_queen_d_4), put(b_queen_d_7));

        chessBoard = play(events, chessBoard).setBoardForGameInProgress();
    }

    @Test
    public void piece_name_format_is_as_follows() {
        assertThat(w_queen_d_4.toString(), equalTo("w QUEEN"));
    }

    @Test
    public void it_finds_all_potentialGameEvents() {
        chessBoard = new BoardSetter().setBoard();

        chessBoard = chessBoard.playEvent(put(w_queen_d_4));
        Square queen_d_4 = square(D, R4);

        List<GameEvent> potentialGameEvents = w_queen_d_4.possibleEvents(chessBoard);

        assertThat(potentialGameEvents.size(), equalTo(19));

        Piece b_pawn_a_7 = newPiece(BLACK, PAWN, a_7);
        Piece b_pawn_d_7 = newPiece(BLACK, PAWN, d_7);
        Piece b_pawn_g_7 = newPiece(BLACK, PAWN, g_7);

        assertTrue(potentialGameEvents.contains(capture(w_queen_d_4, b_pawn_a_7, chessBoard)));
        assertTrue(potentialGameEvents.contains(capture(w_queen_d_4, b_pawn_d_7, chessBoard)));
        assertTrue(potentialGameEvents.contains(capture(w_queen_d_4, b_pawn_g_7, chessBoard)));

        assertTrue(potentialGameEvents.contains(move(queen_d_4, c_3)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, d_3)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, e_3)));

        assertTrue(potentialGameEvents.contains(move(queen_d_4, a_4)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, b_4)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, c_4)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, e_4)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, f_4)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, g_4)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, h_4)));

        assertTrue(potentialGameEvents.contains(move(queen_d_4, c_5)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, b_6)));

        assertTrue(potentialGameEvents.contains(move(queen_d_4, d_5)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, d_6)));

        assertTrue(potentialGameEvents.contains(move(queen_d_4, e_5)));
        assertTrue(potentialGameEvents.contains(move(queen_d_4, f_6)));
    }

    @Test
    public void it_finds_only_legal_potentialGameEvents() {
        chessBoard = new BoardSetter().setBoard();

        chessBoard = chessBoard.playEvent(put(w_queen_d_4));

        List<GameEvent> potentialGameEvents = w_queen_d_4.possibleEvents(chessBoard);

        chessBoard = chessBoard.setBoardForGameInProgress();

        for (GameEvent gameEvent : potentialGameEvents) {
            chessBoard.playEvent(gameEvent);
        }
    }

    @Test
    public void it_finds_piecesAttacked() {
        chessBoard = new BoardSetter().setBoard();

        chessBoard = chessBoard.playEvent(put(w_queen_d_4));

        List<Piece> capturePieces =
            w_queen_d_4.opponentPiecesAttacked(chessBoard);

        assertThat(capturePieces.size(), equalTo(3));
        assertTrue(capturePieces.contains(newPiece(BLACK, PAWN, a_7)));
        assertTrue(capturePieces.contains(newPiece(BLACK, PAWN, d_7)));
        assertTrue(capturePieces.contains(newPiece(BLACK, PAWN, g_7)));
    }

    @Test
    public void it_finds_defendedPieces() {
        chessBoard = new BoardSetter().setBoard();

        chessBoard = chessBoard.playEvent(put(w_queen_d_4));

        List<Piece> capturePieces = w_queen_d_4.teammatesDefended(chessBoard);

        assertThat(capturePieces.size(), equalTo(3));
        assertTrue(capturePieces.contains(newPiece(WHITE, PAWN, b_2)));
        assertTrue(capturePieces.contains(newPiece(WHITE, PAWN, d_2)));
        assertTrue(capturePieces.contains(newPiece(WHITE, PAWN, f_2)));
    }

    @Test
    public void it_finds_attackingPieces() {
        chessBoard = new BoardSetter().setBoard();

        chessBoard = chessBoard.playEvent(put(w_queen_d_6));

        List<Piece> attackingPieces = w_queen_d_6.opponentsAttackingMe(chessBoard);

        assertThat(attackingPieces.size(), equalTo(2));
        assertTrue(attackingPieces.contains(newPiece(BLACK, PAWN, c_7)));
        assertTrue(attackingPieces.contains(newPiece(BLACK, PAWN, e_7)));
    }

    @Test
    public void it_finds_supportingPieces() {
        chessBoard = new BoardSetter().setBoard();

        chessBoard = chessBoard.playEvent(put(w_queen_d_3));

        List<Piece> supportingPieces = w_queen_d_3.teammatesDefendingMe(chessBoard);

        assertThat(supportingPieces.size(), equalTo(2));
        assertTrue(supportingPieces.contains(newPiece(WHITE, PAWN, c_2)));
        assertTrue(supportingPieces.contains(newPiece(WHITE, PAWN, e_2)));
    }

}