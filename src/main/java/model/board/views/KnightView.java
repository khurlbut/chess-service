package model.board.views;

import model.board.BoardPosition;
import model.enums.Color;
import model.enums.ViewVector;
import model.enums.TravelDistance;

public final class KnightView extends RadiatingView {

    private static final ViewVector[] KNIGHT_DIRECTIONS = { ViewVector.RIGHT_UP_UP, ViewVector.RIGHT_RIGHT_UP,
            ViewVector.RIGHT_RIGHT_DOWN, ViewVector.RIGHT_DOWN_DOWN, ViewVector.LEFT_DOWN_DOWN,
            ViewVector.LEFT_LEFT_DOWN, ViewVector.LEFT_LEFT_UP, ViewVector.LEFT_UP_UP };

    KnightView(Color color, BoardPosition boardPosition) {
        super(color, boardPosition, KNIGHT_DIRECTIONS, TravelDistance.ONE_UNIT_SQUARE);
    }

}
