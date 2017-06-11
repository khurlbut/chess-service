package model.board;

import static model.enums.ViewVector.LEFT;
import static model.enums.ViewVector.RIGHT;

import java.util.ArrayList;
import java.util.List;

import model.enums.Color;
import model.enums.Rank;
import model.piece.Piece;

public final class EnPassantEnabler {

	public static final ChessBoard enableNextMoveEnPassants(GameEvent event, ChessBoard chessBoard) {
		if (event instanceof MoveEvent) {
			MoveEvent move = (MoveEvent) event;
			Piece p = chessBoard.pieceAt(event.target());
			if (Rank.Pawn == p.rank() && pawnMovedTwoRows(move)) {
				List<Square> squares = enPassantCandidates(move.target(),
						p.color(), chessBoard);

				for (Square s : squares) {
					EnPassantEnableEvent e = new EnPassantEnableEvent(s, move.target());
					chessBoard = e.playEvent(chessBoard);
				}
			}
		}
		
		return chessBoard;
	}

	private static final boolean pawnMovedTwoRows(MoveEvent move) {
		return Math.abs(targetRow(move) - sourceRow(move)) == 2;
	}

	private static final int sourceRow(MoveEvent move) {
		return move.source().row().ordinal();
	}

	private static final int targetRow(MoveEvent move) {
		return move.target().row().ordinal();
	}

	private static final List<Square> enPassantCandidates(Square target, Color color, ChessBoard chessBoard) {
		Square left = target.neighbor(LEFT);
		Square right = target.neighbor(RIGHT);

		List<Square> candidateSquares = new ArrayList<Square>();
		if (isOpponentPawn(left, color, chessBoard)) {
			candidateSquares.add(left);
		}
		if (isOpponentPawn(right, color, chessBoard)) {
			candidateSquares.add(right);
		}

		return candidateSquares;
	}

	private static final boolean isOpponentPawn(Square square, Color color, ChessBoard chessBoard) {
		Piece p = chessBoard.pieceAt(square);
		if (p != null) {
			Color opponentColor = color.opponentColor();
			if (p.rank() == Rank.Pawn && p.color() == opponentColor) {
				return true;
			}
		}
		return false;
	}


}
