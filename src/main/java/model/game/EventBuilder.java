package model.game;

import static model.board.Sugar.capture;
import static model.board.Sugar.enPassantCapture;
import static model.board.Sugar.enPassanteTarget;
import static model.board.Sugar.isEnPassant;
import static model.board.Sugar.move;
import static service.SquareTranslator.boardNumberToSquare;
import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.Square;
import model.piece.Piece;

public class EventBuilder {

	public static final GameEvent getEvent(String from, String to, ChessBoard board) {
		if (from == null || to == null || board == null) {
			throw new IllegalArgumentException(
					"Null paramaters are not allowed!");
		}
		
		Square fromSquare = boardNumberToSquare(from);
		Square toSquare = boardNumberToSquare(to);

		return getEvent(fromSquare, toSquare, board);
	}
	
	private static final GameEvent getEvent(Square fromSquare, Square toSquare,
			ChessBoard board) {
		
		if (fromSquare == null || toSquare == null || board == null) {
			throw new IllegalArgumentException(
					"Null paramaters are not allowed!");
		}

		Piece moving = board.pieceAt(fromSquare);
		Piece target = board.pieceAt(toSquare);

		if (isEnPassant(moving, fromSquare, toSquare)) {
			Square captureSquare = enPassanteTarget(fromSquare, toSquare);
			return enPassantCapture(fromSquare, toSquare, captureSquare);
		}

		if (target != null) {
			return capture(fromSquare, toSquare, target);
		}

		return move(fromSquare, toSquare);
	}

}
