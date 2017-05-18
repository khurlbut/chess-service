package model.board.views;

import model.board.BoardPosition;
import model.enums.Color;
import model.enums.ViewVector;

public final class BishopView extends RadiatingView {

    private static final ViewVector[] BISHOP_MOVES = { ViewVector.RIGHT_UP, ViewVector.RIGHT_DOWN,
            ViewVector.LEFT_UP, ViewVector.LEFT_DOWN };

    BishopView(Color color, BoardPosition boardPosition) {
        super(color, boardPosition, BISHOP_MOVES);
    }

}
