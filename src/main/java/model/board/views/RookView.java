package model.board.views;

import model.board.BoardPosition;
import model.enums.Color;
import model.enums.ViewVector;

public final class RookView extends RadiatingView {

    private static final ViewVector[] ROOK_MOVES = { ViewVector.UP, ViewVector.DOWN, ViewVector.LEFT,
            ViewVector.RIGHT };

    public RookView(Color color, BoardPosition boardPosition) {
        super(color, boardPosition, ROOK_MOVES);
    }

}
