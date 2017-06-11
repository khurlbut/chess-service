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

		if (Rank.Pawn == rank) {
			piece = new Pawn(color, rank, homeSquare, false);
		} else if (Rank.King == rank || Rank.Rook == rank) {
			piece = newPiece(color, rank, homeSquare, false);
		} else {
			piece = new Piece(color, rank, homeSquare);
		}

		return piece;
	}

	public static MovementTrackablePiece newPiece(MovementTrackablePiece piece) {
		if (piece.rank() == Rank.Pawn) {
			return new Pawn(piece.color(), piece.rank(), piece.homeSquare(), true);
		}
		return newPiece(piece.color(), piece.rank(), piece.homeSquare(), true);
	}
	
	public static Piece newEnPassantEnabledPawn(Pawn pawn, Square target) {
		return new Pawn(pawn, target);
	}
	

	public static Piece newEnPassantDisabledPawn(Pawn pawn) {
		return new Pawn(pawn, null);
	}


	private static MovementTrackablePiece newPiece(Color color, Rank rank,
			Square homeSquare, boolean hasMoved) {
		if (rank == Rank.Pawn) {
			return new Pawn(color, rank, homeSquare, hasMoved);
		}
		return new MovementTrackablePiece(color, rank, homeSquare, hasMoved);
	}

}
