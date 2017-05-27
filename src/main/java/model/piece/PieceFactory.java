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
			piece = newPawn(color, rank, homeSquare, false);
		} else if (Rank.King == rank || Rank.Rook == rank || Rank.Pawn == rank) {
			piece = newPiece(color, rank, homeSquare, false);
		} else {
			piece = new Piece(color, rank, homeSquare);
		}

		return piece;
	}
	
	public static MovementTrackablePiece newPiece(MovementTrackablePiece piece) {
		return newPiece(piece.color(), piece.rank(), piece.homeSquare(), true);
	}
	
	public static MovementTrackablePiece newPawn(Pawn pawn) {
		return newPawn(pawn.color(), pawn.rank(), pawn.homeSquare(), true);
	}
	
	private static MovementTrackablePiece newPiece(Color color, Rank rank, Square homeSquare,
			boolean hasMoved) {
		return new MovementTrackablePiece(color, rank, homeSquare, hasMoved);
	}
	
	private static MovementTrackablePiece newPawn(Color color, Rank rank, Square homeSquare, boolean hasMoved) {
		return new Pawn(color, rank, homeSquare, hasMoved);
	}

}
