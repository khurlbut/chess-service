package model.game;

import static model.board.Sugar.capture;
import static model.board.Sugar.move;
import static model.enums.ViewVector.LEFT;
import static model.enums.ViewVector.RIGHT;
import static service.SquareTranslator.boardNumberToSquare;
import model.board.ChessBoard;
import model.board.EnPassantCaptureEvent;
import model.board.GameEvent;
import model.board.Square;
import model.piece.Pawn;
import model.piece.Piece;

public final class EventBuilder {

	public static final GameEvent getEvent(int from, int to, ChessBoard board) {
		Square fromSquare = boardNumberToSquare(from);
		Square toSquare = boardNumberToSquare(to);

		return getEvent(fromSquare, toSquare, board);
	}
	
	private static final GameEvent getEvent(Square fromSquare, Square toSquare,
			ChessBoard board) {
		
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

	private static boolean isEnPassant(Piece movingPiece, Square source,
			Square target) {
		
		if (movingPiece instanceof Pawn) {
			Pawn pawn = (Pawn) movingPiece;
			if (pawn.hasEnPassantCapture() && (source.col() != target.col())) {
				return true;
			}
		}
		
		return false;
	}

	private static EnPassantCaptureEvent enPassantCapture(
			Square attackingSquare, Square targetSquare, Square captureSquare) {
		
		return new EnPassantCaptureEvent(attackingSquare, targetSquare,
				captureSquare);
	}

	private static Square enPassanteTarget(Square source, Square target) {
		Square captureTarget = null;

		if (col(source) < col(target)) {
			captureTarget = source.neighbor(RIGHT);
		} else if (col(source) > col(target)) {
			captureTarget = source.neighbor(LEFT);
		} else {
			throw new IllegalArgumentException("Invalid En-Passant Attempt!");
		}

		return captureTarget;
	}

	private static int col(Square source) {
		return source.col().ordinal();
	}

}
