package model.piece;

import model.board.Square;
import model.enums.Color;
import model.enums.Rank;
import model.exceptions.ConstructorArgsException;

public class PieceFactory {

	public static Piece newPiece(Color color, Rank rank, Square homeSquare) {
		Piece piece = null;

		if (color == null || rank == null || homeSquare == null) {
			throw new ConstructorArgsException("Arguments must not be null!");
		}

		if (Rank.King == rank || Rank.Rook == rank || Rank.Pawn == rank) {
			piece = newPiece(color, rank, homeSquare, false);
		} else {
			piece = new Piece(color, rank, homeSquare);
		}

		return piece;
	}
	
	public static MovementTrackablePiece newPiece(MovementTrackablePiece piece) {
		return newPiece(piece.color(), piece.rank(), piece.homeSquare(), true);
	}
	
	public static MovementTrackablePiece newPiece(Color color, Rank rank, Square homeSquare,
			boolean hasMoved) {
		return new MovementTrackablePiece(color, rank, homeSquare, hasMoved);
	}

}
