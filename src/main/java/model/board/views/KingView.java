package model.board.views;

import static model.board.Sugar.hasMoved;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.board.BoardPosition;
import model.board.Square;
import model.enums.Color;
import model.enums.Column;
import model.enums.Row;
import model.enums.TravelDistance;
import model.enums.ViewVector;
import model.piece.MovementTrackablePiece;
import model.piece.Piece;

public final class KingView extends RadiatingView {

	private static final int CASTLE_ON_H = 2;

	private static final int CASTLE_ON_A = 3;

	private static final ViewVector[] KING_MOVES = { ViewVector.UP,
			ViewVector.RIGHT_UP, ViewVector.RIGHT, ViewVector.RIGHT_DOWN,
			ViewVector.DOWN, ViewVector.LEFT_DOWN, ViewVector.LEFT,
			ViewVector.LEFT_UP };

	private final Square viewPoint;

	KingView(Color color, BoardPosition boardPosition) {
		super(color, boardPosition, KING_MOVES, TravelDistance.ONE_UNIT_SQUARE);
		viewPoint = boardPosition.square();
	}

	@Override
	public List<Square> moveToSquares() {
		List<Square> safeSquares = new ArrayList<Square>();
		List<Square> moveToSquares = super.moveToSquares();
		Set<Square> localMoveToSquares = new HashSet<Square>(moveToSquares);

		localMoveToSquares = addCastleSquares(localMoveToSquares, Column.A,
				CASTLE_ON_A);
		localMoveToSquares = addCastleSquares(localMoveToSquares, Column.H,
				CASTLE_ON_H);

		for (Square s : localMoveToSquares) {
			if (!squareIsUnderAttack(s)) {
				safeSquares.add(s);
			}
		}

		return safeSquares;
	}

	private Set<Square> addCastleSquares(Set<Square> moveToSquares,
			Column rookCol, int columnsToCheck) {

		Column kingColumn = viewPoint.col();
		Row thisRow = viewPoint.row();

		if (kingAndRookHaveNotMoved(rookCol, thisRow)) {
			int dir = (CASTLE_ON_A == columnsToCheck ? -1 : 1);

			for (int c = 1; c <= columnsToCheck; c++) {
				Column thisCol = kingColumn.horizontalNeighbor(dir * c);
				Square s = new Square(thisCol, thisRow);
				if (chessBoard.pieceAt(s) != null || squareIsUnderAttack(s)) {
					return moveToSquares;
				}
			}
			Column moveToColumn = dir < 0 ? Column.C : Column.G;
			moveToSquares.add(new Square(moveToColumn, thisRow));
		}

		return moveToSquares;
	}

	private boolean kingAndRookHaveNotMoved(Column rookColumn, Row rookRow) {
		Piece kingPiece = chessBoard.pieceAt(viewPoint);
		Piece rookPiece = chessBoard.pieceAt(new Square(rookColumn, rookRow));

		if (kingPiece == null || rookPiece == null) {
			return false;
		}
		if (!(kingPiece instanceof MovementTrackablePiece)) {
			throw new IllegalArgumentException(
					"King must be a MovementTrackable piece!");
		}
		if (!(rookPiece instanceof MovementTrackablePiece)) {
			throw new IllegalArgumentException(
					"Rook must be a MovementTrackable piece!");
		}

		MovementTrackablePiece king = (MovementTrackablePiece) kingPiece;
		MovementTrackablePiece rook = (MovementTrackablePiece) rookPiece;

		if (!king.hasMoved() && !rook.hasMoved()) {
			return true;
		}

		return false;
	}

	private boolean squareIsUnderAttack(Square availableSquare) {
		return piecesAttackingAt(availableSquare).size() != 0;
	}

	private List<Piece> piecesAttackingAt(Square square) {
		List<Piece> attackingPieces = new ArrayList<Piece>();
		List<Piece> opponentPieces = chessBoard.piecesFor(viewColor
				.opponentColor());

		for (Piece opponentPiece : opponentPieces) {
			if (opponentPiece.threatenedSquares(chessBoard).contains(square)) {
				attackingPieces.add(opponentPiece);
			}
		}

		return attackingPieces;
	}

}
