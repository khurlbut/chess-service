package model.board;

import model.enums.GameEventType;

public interface GameEvent {

    Square target();

    Square source();

    ChessBoard playEvent(ChessBoard chessBoard);

    GameEventType type();

}
