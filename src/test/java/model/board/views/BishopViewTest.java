package model.board.views;

import static model.board.Sugar.eventList;
import static model.board.Sugar.play;
import static model.board.Sugar.position;
import static model.board.Sugar.put;
import static model.board.Sugar.square;
import static model.board.views.ViewSugar.bishopView;
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
import static model.enums.Row.R1;
import static model.enums.Row.R2;
import static model.enums.Row.R3;
import static model.enums.Row.R4;
import static model.enums.Row.R5;
import static model.enums.Row.R6;
import static model.enums.Row.R7;
import static model.enums.Row.R8;
import static model.piece.PieceFactory.newPiece;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.PutEvent;
import model.board.Square;
import model.enums.Color;
import model.enums.Column;
import model.enums.Row;
import model.piece.Piece;

import org.junit.Before;
import org.junit.Test;

public class BishopViewTest {

    private BishopView w_bishopView_e_4 = null;
    private BishopView b_bishopView_e_4 = null;

    private static final Square E5 = square(E, R5);
    private static final Square F5 = square(F, R5);
    private static final Square F4 = square(F, R4);
    private static final Square F3 = square(F, R3);
    private static final Square E3 = square(E, R3);
    private static final Square D3 = square(D, R3);
    private static final Square D4 = square(D, R4);
    private static final Square D5 = square(D, R5);

    private static final Square E6 = square(E, R6);
    private static final Square F6 = square(F, R6);
    private static final Square G6 = square(G, R6);
    private static final Square G5 = square(G, R5);
    private static final Square G4 = square(G, R4);
    private static final Square G3 = square(G, R3);
    private static final Square G2 = square(G, R2);
    private static final Square F2 = square(F, R2);
    private static final Square E2 = square(E, R2);
    private static final Square D2 = square(D, R2);
    private static final Square C2 = square(C, R2);
    private static final Square C3 = square(C, R3);
    private static final Square C4 = square(C, R4);
    private static final Square C5 = square(C, R5);
    private static final Square C6 = square(C, R6);
    private static final Square D6 = square(D, R6);
    private static final Square H7 = square(H, R7);
    private static final Square H1 = square(H, R1);
    private static final Square B1 = square(B, R1);
    private static final Square B7 = square(B, R7);
    private static final Square A8 = square(A, R8);

    private ChessBoard chessBoard;

    @Before
    public void setUp() {
        chessBoard = new ChessBoard();
        w_bishopView_e_4 = newBishopView(WHITE, E, R4);
    }

    @Test
    public void it_finds_move_to_squares() {
        List<Square> moveToSquares = w_bishopView_e_4.moveToSquares();
        assertThat(moveToSquares.size(), equalTo(13));

        assertTrue(moveToSquares.contains(F5));
        assertTrue(moveToSquares.contains(G6));
        assertTrue(moveToSquares.contains(H7));

        assertTrue(moveToSquares.contains(F3));
        assertTrue(moveToSquares.contains(G2));
        assertTrue(moveToSquares.contains(H1));

        assertTrue(moveToSquares.contains(D3));
        assertTrue(moveToSquares.contains(C2));
        assertTrue(moveToSquares.contains(B1));

        assertTrue(moveToSquares.contains(D5));
        assertTrue(moveToSquares.contains(C6));
        assertTrue(moveToSquares.contains(B7));
        assertTrue(moveToSquares.contains(A8));
    }

    @Test
    public void it_finds_threatened_squares() {
        List<Square> threatenedSquares = w_bishopView_e_4.threatenedSquares();
        assertThat(threatenedSquares.size(), equalTo(13));

        assertTrue(threatenedSquares.contains(F5));
        assertTrue(threatenedSquares.contains(G6));
        assertTrue(threatenedSquares.contains(H7));

        assertTrue(threatenedSquares.contains(F3));
        assertTrue(threatenedSquares.contains(G2));
        assertTrue(threatenedSquares.contains(H1));

        assertTrue(threatenedSquares.contains(D3));
        assertTrue(threatenedSquares.contains(C2));
        assertTrue(threatenedSquares.contains(B1));

        assertTrue(threatenedSquares.contains(D5));
        assertTrue(threatenedSquares.contains(C6));
        assertTrue(threatenedSquares.contains(B7));
        assertTrue(threatenedSquares.contains(A8));
    }

    @Test
    public void it_finds_squares_holding_pieces_attacked() {
        chessBoard = putBlackPawnsInBoxAround_E_4();
        chessBoard = putBlackPawnsInBoxWithRadiousTwoAround_E_4();

        w_bishopView_e_4 = newBishopView(WHITE, E, R4);

        List<Square> squaresHoldingPiecesAttacked = w_bishopView_e_4.squaresHoldingPiecesAttacked();
        assertThat(squaresHoldingPiecesAttacked.size(), equalTo(4));

        assertTrue(squaresHoldingPiecesAttacked.contains(F5));
        assertTrue(squaresHoldingPiecesAttacked.contains(F3));
        assertTrue(squaresHoldingPiecesAttacked.contains(D3));
        assertTrue(squaresHoldingPiecesAttacked.contains(D5));
    }

    @Test
    public void it_finds_squares_holding_pieces_defended() {
        chessBoard = putBlackPawnsInBoxAround_E_4();
        chessBoard = putBlackPawnsInBoxWithRadiousTwoAround_E_4();

        b_bishopView_e_4 = newBishopView(BLACK, E, R4);

        List<Square> squaresHoldingPiecesDefended = b_bishopView_e_4.squaresHoldingPiecesDefended();
        assertThat(squaresHoldingPiecesDefended.size(), equalTo(4));

        assertTrue(squaresHoldingPiecesDefended.contains(F5));
        assertTrue(squaresHoldingPiecesDefended.contains(F3));
        assertTrue(squaresHoldingPiecesDefended.contains(D3));
        assertTrue(squaresHoldingPiecesDefended.contains(D5));
    }

    private BishopView newBishopView(Color color, Column column, Row row) {
        return bishopView(color, position(column, row, chessBoard));
    }

    private ChessBoard putBlackPawnsInBoxAround_E_4() {
        Piece b_pawn_e_5 = newPiece(BLACK, PAWN, E5);
        Piece b_pawn_f_5 = newPiece(BLACK, PAWN, F5);
        Piece b_pawn_f_4 = newPiece(BLACK, PAWN, F4);
        Piece b_pawn_f_3 = newPiece(BLACK, PAWN, F3);
        Piece b_pawn_e_3 = newPiece(BLACK, PAWN, E3);
        Piece b_pawn_d_3 = newPiece(BLACK, PAWN, D3);
        Piece b_pawn_d_4 = newPiece(BLACK, PAWN, D4);
        Piece b_pawn_d_5 = newPiece(BLACK, PAWN, D5);

        PutEvent put_b_pawn_e_5 = put(b_pawn_e_5);
        PutEvent put_b_pawn_f_5 = put(b_pawn_f_5);
        PutEvent put_b_pawn_f_4 = put(b_pawn_f_4);
        PutEvent put_b_pawn_f_3 = put(b_pawn_f_3);
        PutEvent put_b_pawn_e_3 = put(b_pawn_e_3);
        PutEvent put_b_pawn_d_3 = put(b_pawn_d_3);
        PutEvent put_b_pawn_d_4 = put(b_pawn_d_4);
        PutEvent put_b_pawn_d_5 = put(b_pawn_d_5);

        List<GameEvent> putEvents =
            eventList(put_b_pawn_e_5, put_b_pawn_f_5, put_b_pawn_f_4, put_b_pawn_f_3, put_b_pawn_e_3,
                put_b_pawn_d_3, put_b_pawn_d_4, put_b_pawn_d_5);

        return play(putEvents, chessBoard);

    }

    private ChessBoard putBlackPawnsInBoxWithRadiousTwoAround_E_4() {
        Piece b_pawn_e_6 = newPiece(BLACK, PAWN, E6);
        Piece b_pawn_f_6 = newPiece(BLACK, PAWN, F6);
        Piece b_pawn_g_6 = newPiece(BLACK, PAWN, G6);
        Piece b_pawn_g_5 = newPiece(BLACK, PAWN, G5);
        Piece b_pawn_g_4 = newPiece(BLACK, PAWN, G4);
        Piece b_pawn_g_3 = newPiece(BLACK, PAWN, G3);
        Piece b_pawn_g_2 = newPiece(BLACK, PAWN, G2);
        Piece b_pawn_f_2 = newPiece(BLACK, PAWN, F2);
        Piece b_pawn_e_2 = newPiece(BLACK, PAWN, E2);
        Piece b_pawn_d_2 = newPiece(BLACK, PAWN, D2);
        Piece b_pawn_c_2 = newPiece(BLACK, PAWN, C2);
        Piece b_pawn_c_3 = newPiece(BLACK, PAWN, C3);
        Piece b_pawn_c_4 = newPiece(BLACK, PAWN, C4);
        Piece b_pawn_c_5 = newPiece(BLACK, PAWN, C5);
        Piece b_pawn_c_6 = newPiece(BLACK, PAWN, C6);
        Piece b_pawn_d_6 = newPiece(BLACK, PAWN, D6);

        PutEvent put_b_pawn_e_6 = put(b_pawn_e_6);
        PutEvent put_b_pawn_f_6 = put(b_pawn_f_6);
        PutEvent put_b_pawn_g_6 = put(b_pawn_g_6);
        PutEvent put_b_pawn_g_5 = put(b_pawn_g_5);
        PutEvent put_b_pawn_g_4 = put(b_pawn_g_4);
        PutEvent put_b_pawn_g_3 = put(b_pawn_g_3);
        PutEvent put_b_pawn_g_2 = put(b_pawn_g_2);
        PutEvent put_b_pawn_f_2 = put(b_pawn_f_2);
        PutEvent put_b_pawn_e_2 = put(b_pawn_e_2);
        PutEvent put_b_pawn_d_2 = put(b_pawn_d_2);
        PutEvent put_b_pawn_c_2 = put(b_pawn_c_2);
        PutEvent put_b_pawn_c_3 = put(b_pawn_c_3);
        PutEvent put_b_pawn_c_4 = put(b_pawn_c_4);
        PutEvent put_b_pawn_c_5 = put(b_pawn_c_5);
        PutEvent put_b_pawn_c_6 = put(b_pawn_c_6);
        PutEvent put_b_pawn_d_6 = put(b_pawn_d_6);

        List<GameEvent> putEvents =
            eventList(put_b_pawn_e_6, put_b_pawn_f_6, put_b_pawn_g_6, put_b_pawn_g_5, put_b_pawn_g_4,
                put_b_pawn_g_3, put_b_pawn_g_2, put_b_pawn_f_2, put_b_pawn_e_2, put_b_pawn_d_2, put_b_pawn_c_2,
                put_b_pawn_c_3, put_b_pawn_c_4, put_b_pawn_c_5, put_b_pawn_c_6, put_b_pawn_d_6);

        return play(putEvents, chessBoard);
    }

}
