package model.board;

import static model.board.Sugar.eventList;
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

import java.util.ArrayList;
import java.util.List;

public final class BoardSetter {
    private List<GameEvent> putEvents = new ArrayList<GameEvent>();

    public BoardSetter() {
        putEvents.addAll(eventList(w_King_e_1, b_King_e_8, w_Queen_d_1, b_Queen_d_8));
        putEvents.addAll(eventList(w_Bishop_c_1, b_Bishop_c_8, w_Bishop_f_1, b_Bishop_f_8));
        putEvents.addAll(eventList(w_Knight_b_1, b_Knight_b_8, w_Knight_g_1, b_Knight_g_8));
        putEvents.addAll(eventList(w_Rook_a_1, b_Rook_a_8, w_Rook_h_1, b_Rook_h_8));
        putEvents.addAll(eventList(w_Pawn_a_2, w_Pawn_b_2, w_Pawn_c_2, w_Pawn_d_2));
        putEvents.addAll(eventList(b_Pawn_a_7, b_Pawn_b_7, b_Pawn_c_7, b_Pawn_d_7));
        putEvents.addAll(eventList(w_Pawn_e_2, w_Pawn_f_2, w_Pawn_g_2, w_Pawn_h_2));
        putEvents.addAll(eventList(b_Pawn_e_7, b_Pawn_f_7, b_Pawn_g_7, b_Pawn_h_7));
    }

    public ChessBoard setBoard() {
        ChessBoard board = new ChessBoard();

        board = play(putEvents, board);

        return board;
    }

    private PutEvent b_King_e_8 = put(BLACK, KING, square(E, R8));
    private PutEvent w_King_e_1 = put(WHITE, KING, square(E, R1));
    private PutEvent b_Queen_d_8 = put(BLACK, QUEEN, square(D, R8));
    private PutEvent w_Queen_d_1 = put(WHITE, QUEEN, square(D, R1));

    private PutEvent b_Bishop_c_8 = put(BLACK, BISHOP, square(C, R8));
    private PutEvent b_Bishop_f_8 = put(BLACK, BISHOP, square(F, R8));
    private PutEvent w_Bishop_c_1 = put(WHITE, BISHOP, square(C, R1));
    private PutEvent w_Bishop_f_1 = put(WHITE, BISHOP, square(F, R1));

    private PutEvent b_Knight_b_8 = put(BLACK, KNIGHT, square(B, R8));
    private PutEvent b_Knight_g_8 = put(BLACK, KNIGHT, square(G, R8));
    private PutEvent w_Knight_b_1 = put(WHITE, KNIGHT, square(B, R1));
    private PutEvent w_Knight_g_1 = put(WHITE, KNIGHT, square(G, R1));

    private PutEvent b_Rook_a_8 = put(BLACK, ROOK, square(A, R8));
    private PutEvent b_Rook_h_8 = put(BLACK, ROOK, square(H, R8));
    private PutEvent w_Rook_a_1 = put(WHITE, ROOK, square(A, R1));
    private PutEvent w_Rook_h_1 = put(WHITE, ROOK, square(H, R1));

    private PutEvent b_Pawn_a_7 = put(BLACK, PAWN, square(A, R7));
    private PutEvent b_Pawn_b_7 = put(BLACK, PAWN, square(B, R7));
    private PutEvent b_Pawn_c_7 = put(BLACK, PAWN, square(C, R7));
    private PutEvent b_Pawn_d_7 = put(BLACK, PAWN, square(D, R7));
    private PutEvent b_Pawn_e_7 = put(BLACK, PAWN, square(E, R7));
    private PutEvent b_Pawn_f_7 = put(BLACK, PAWN, square(F, R7));
    private PutEvent b_Pawn_g_7 = put(BLACK, PAWN, square(G, R7));
    private PutEvent b_Pawn_h_7 = put(BLACK, PAWN, square(H, R7));

    private PutEvent w_Pawn_a_2 = put(WHITE, PAWN, square(A, R2));
    private PutEvent w_Pawn_b_2 = put(WHITE, PAWN, square(B, R2));
    private PutEvent w_Pawn_c_2 = put(WHITE, PAWN, square(C, R2));
    private PutEvent w_Pawn_d_2 = put(WHITE, PAWN, square(D, R2));
    private PutEvent w_Pawn_e_2 = put(WHITE, PAWN, square(E, R2));
    private PutEvent w_Pawn_f_2 = put(WHITE, PAWN, square(F, R2));
    private PutEvent w_Pawn_g_2 = put(WHITE, PAWN, square(G, R2));
    private PutEvent w_Pawn_h_2 = put(WHITE, PAWN, square(H, R2));
}
