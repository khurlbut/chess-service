package model.board.views;

import model.board.BoardPosition;
import model.enums.Color;

public class ViewSugar {

    public static KingView kingView(Color color, BoardPosition position) {
        return new KingView(color, position);
    }

    public static QueenView queenView(Color color, BoardPosition position) {
        return new QueenView(color, position);
    }

    public static BishopView bishopView(Color color, BoardPosition position) {
        return new BishopView(color, position);
    }

    public static KnightView knightView(Color color, BoardPosition position) {
        return new KnightView(color, position);
    }

    public static RookView rookView(Color color, BoardPosition position) {
        return new RookView(color, position);
    }

    public static PawnView pawnView(Color color, BoardPosition position) {
        return new PawnView(color, position);
    }

}
