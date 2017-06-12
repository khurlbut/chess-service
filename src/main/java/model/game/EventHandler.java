package model.game;
import static model.board.EnPassantDisabler.disablePreviousMoveEnPassants;
import static model.board.EnPassantEnabler.enableNextMoveEnPassants;
import static model.board.Sugar.isCheck;
import static model.board.Sugar.findKing;
import model.board.ChessBoard;
import model.board.GameEvent;
import model.enums.Color;
import model.exceptions.IllegalGameEventException;

public final class EventHandler {

	public static final ChessBoard handleEvent(GameEvent event, ChessBoard chessBoard) {
		Color color = color(event, chessBoard);
		
		chessBoard = chessBoard.playEvent(event);
		chessBoard = enableNextMoveEnPassants(event, chessBoard);
		chessBoard = disablePreviousMoveEnPassants(chessBoard, color);

		return chessBoard;
	}

	private static Color color(GameEvent event, ChessBoard chessBoard) {
		return chessBoard.pieceAt(event.source()).color();
	}

}
