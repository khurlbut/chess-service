package model.board;

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
import static model.enums.Rank.BISHOP;
import static model.enums.Rank.KING;
import static model.enums.Rank.KNIGHT;
import static model.enums.Rank.PAWN;
import static model.enums.Rank.QUEEN;
import static model.enums.Rank.ROOK;
import static model.enums.Row.R1;
import static model.enums.Row.R2;
import static model.enums.Row.R7;
import static model.enums.Row.R8;
import static model.piece.PieceFactory.newPiece;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import model.piece.Piece;

import org.junit.Before;
import org.junit.Test;

public class BoardSetterTest {
    private Square a_1 = square(A, R1);
    private Square b_1 = square(B, R1);
    private Square c_1 = square(C, R1);
    private Square d_1 = square(D, R1);
    private Square e_1 = square(E, R1);
    private Square f_1 = square(F, R1);
    private Square g_1 = square(G, R1);
    private Square h_1 = square(H, R1);

    private Square a_2 = square(A, R2);
    private Square b_2 = square(B, R2);
    private Square c_2 = square(C, R2);
    private Square d_2 = square(D, R2);
    private Square e_2 = square(E, R2);
    private Square f_2 = square(F, R2);
    private Square g_2 = square(G, R2);
    private Square h_2 = square(H, R2);

    private Square a_7 = square(A, R7);
    private Square b_7 = square(B, R7);
    private Square c_7 = square(C, R7);
    private Square d_7 = square(D, R7);
    private Square e_7 = square(E, R7);
    private Square f_7 = square(F, R7);
    private Square g_7 = square(G, R7);
    private Square h_7 = square(H, R7);

    private Square a_8 = square(A, R8);
    private Square b_8 = square(B, R8);
    private Square c_8 = square(C, R8);
    private Square d_8 = square(D, R8);
    private Square e_8 = square(E, R8);
    private Square f_8 = square(F, R8);
    private Square g_8 = square(G, R8);
    private Square h_8 = square(H, R8);

    private Piece w_pawn_a_2 = newPiece(WHITE, PAWN, a_2);
    private Piece w_pawn_b_2 = newPiece(WHITE, PAWN, b_2);
    private Piece w_pawn_c_2 = newPiece(WHITE, PAWN, c_2);
    private Piece w_pawn_d_2 = newPiece(WHITE, PAWN, d_2);
    private Piece w_pawn_e_2 = newPiece(WHITE, PAWN, e_2);
    private Piece w_pawn_f_2 = newPiece(WHITE, PAWN, f_2);
    private Piece w_pawn_g_2 = newPiece(WHITE, PAWN, g_2);
    private Piece w_pawn_h_2 = newPiece(WHITE, PAWN, h_2);

    private Piece b_pawn_a_7 = newPiece(BLACK, PAWN, a_7);
    private Piece b_pawn_b_7 = newPiece(BLACK, PAWN, b_7);
    private Piece b_pawn_c_7 = newPiece(BLACK, PAWN, c_7);
    private Piece b_pawn_d_7 = newPiece(BLACK, PAWN, d_7);
    private Piece b_pawn_e_7 = newPiece(BLACK, PAWN, e_7);
    private Piece b_pawn_f_7 = newPiece(BLACK, PAWN, f_7);
    private Piece b_pawn_g_7 = newPiece(BLACK, PAWN, g_7);
    private Piece b_pawn_h_7 = newPiece(BLACK, PAWN, h_7);

    private Piece w_rook_a_1 = newPiece(WHITE, ROOK, a_1);
    private Piece w_rook_h_1 = newPiece(WHITE, ROOK, h_1);
    private Piece w_knight_b_1 = newPiece(WHITE, KNIGHT, b_1);
    private Piece w_knight_g_1 = newPiece(WHITE, KNIGHT, g_1);
    private Piece w_bishop_c_1 = newPiece(WHITE, BISHOP, c_1);
    private Piece w_bishop_f_1 = newPiece(WHITE, BISHOP, f_1);
    private Piece w_queen = newPiece(WHITE, QUEEN, d_1);
    private Piece w_king = newPiece(WHITE, KING, e_1);

    private Piece b_rook_a_8 = newPiece(BLACK, ROOK, a_8);
    private Piece b_rook_h_8 = newPiece(BLACK, ROOK, h_8);
    private Piece b_knight_b_8 = newPiece(BLACK, KNIGHT, b_8);
    private Piece b_knight_g_8 = newPiece(BLACK, KNIGHT, g_8);
    private Piece b_bishop_c_8 = newPiece(BLACK, BISHOP, c_8);
    private Piece b_bishop_f_8 = newPiece(BLACK, BISHOP, f_8);
    private Piece b_queen = newPiece(BLACK, QUEEN, d_8);
    private Piece b_king = newPiece(BLACK, KING, e_8);

    private BoardSetter boardSetter;

    @Before
    public void setUp() {
        boardSetter = new BoardSetter();
    }

    @Test
    public void all_the_pieces_are_in_the_correct_places() {
        ChessBoard chessBoard = boardSetter.setBoard();

        assertWhitePawnsInPlace(chessBoard);
        assertBlackPawnsInPlace(chessBoard);
        assertWhitePiecesInPlace(chessBoard);
        assertBlackPiecesInPlace(chessBoard);
    }

    private void assertWhitePawnsInPlace(ChessBoard board) {
        assertThat(board.pieceAt(a_2), equalTo(w_pawn_a_2));
        assertThat(board.pieceAt(b_2), equalTo(w_pawn_b_2));
        assertThat(board.pieceAt(c_2), equalTo(w_pawn_c_2));
        assertThat(board.pieceAt(d_2), equalTo(w_pawn_d_2));
        assertThat(board.pieceAt(e_2), equalTo(w_pawn_e_2));
        assertThat(board.pieceAt(f_2), equalTo(w_pawn_f_2));
        assertThat(board.pieceAt(g_2), equalTo(w_pawn_g_2));
        assertThat(board.pieceAt(h_2), equalTo(w_pawn_h_2));
    }

    private void assertBlackPawnsInPlace(ChessBoard board) {
        assertThat(board.pieceAt(a_7), equalTo(b_pawn_a_7));
        assertThat(board.pieceAt(b_7), equalTo(b_pawn_b_7));
        assertThat(board.pieceAt(c_7), equalTo(b_pawn_c_7));
        assertThat(board.pieceAt(d_7), equalTo(b_pawn_d_7));
        assertThat(board.pieceAt(e_7), equalTo(b_pawn_e_7));
        assertThat(board.pieceAt(f_7), equalTo(b_pawn_f_7));
        assertThat(board.pieceAt(g_7), equalTo(b_pawn_g_7));
        assertThat(board.pieceAt(h_7), equalTo(b_pawn_h_7));
    }

    private void assertWhitePiecesInPlace(ChessBoard board) {
        assertThat(board.pieceAt(a_1), equalTo(w_rook_a_1));
        assertThat(board.pieceAt(b_1), equalTo(w_knight_b_1));
        assertThat(board.pieceAt(c_1), equalTo(w_bishop_c_1));
        assertThat(board.pieceAt(d_1), equalTo(w_queen));
        assertThat(board.pieceAt(e_1), equalTo(w_king));
        assertThat(board.pieceAt(f_1), equalTo(w_bishop_f_1));
        assertThat(board.pieceAt(g_1), equalTo(w_knight_g_1));
        assertThat(board.pieceAt(h_1), equalTo(w_rook_h_1));
    }

    private void assertBlackPiecesInPlace(ChessBoard board) {
        assertThat(board.pieceAt(a_8), equalTo(b_rook_a_8));
        assertThat(board.pieceAt(b_8), equalTo(b_knight_b_8));
        assertThat(board.pieceAt(c_8), equalTo(b_bishop_c_8));
        assertThat(board.pieceAt(d_8), equalTo(b_queen));
        assertThat(board.pieceAt(e_8), equalTo(b_king));
        assertThat(board.pieceAt(f_8), equalTo(b_bishop_f_8));
        assertThat(board.pieceAt(g_8), equalTo(b_knight_g_8));
        assertThat(board.pieceAt(h_8), equalTo(b_rook_h_8));
    }

}
