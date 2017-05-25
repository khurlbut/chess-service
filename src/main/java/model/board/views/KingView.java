package model.board.views;

import static model.board.Sugar.hasMoved;

import java.util.ArrayList;
import java.util.List;

import model.board.BoardPosition;
import model.board.Square;
import model.enums.Color;
import model.enums.Column;
import model.enums.Row;
import model.enums.TravelDistance;
import model.enums.ViewVector;
import model.piece.Piece;
import model.piece.Piece;

public final class KingView extends RadiatingView {

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

		for (Square moveToSquare : moveToSquares) {
			if (!squareIsUnderAttack(moveToSquare)) {
				safeSquares.add(moveToSquare);
			}
		}
		Piece thisKing = chessBoard.pieceAt(viewPoint);

		if (thisKing != null) {
			Square squareA = new Square(Column.A, Row.R1);

			Piece rookA = chessBoard.pieceAt(squareA);

			if (!hasMoved(thisKing, viewPoint)) {
				if (!hasMoved(rookA, squareA)) {
					Square neighborLeftOne = viewPoint
							.neighbor(ViewVector.LEFT);
					if (chessBoard.pieceAt(neighborLeftOne) == null) {
						Square neighborLeftTwo = neighborLeftOne
								.neighbor(ViewVector.LEFT);
						if (chessBoard.pieceAt(neighborLeftTwo) == null) {
							Square neighborLeftThree = neighborLeftTwo
									.neighbor(ViewVector.LEFT);
							if (chessBoard.pieceAt(neighborLeftThree) == null) {
								safeSquares.add(neighborLeftTwo);
							}
						}
					}
				}
			}
		}

		return safeSquares;
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
