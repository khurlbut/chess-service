package model.piece;

import static model.piece.PieceFactory.newPiece;
import model.board.Square;
import model.enums.Color;
import model.enums.Rank;

public class Pawn extends MovementTrackablePiece {
	
	Pawn(Color color, Rank rank, Square homeSquare, boolean hasMoved) {
		super(color, rank, homeSquare, hasMoved);
	}

	public Piece promoteTo(Rank rank) {
		return newPiece(color(), rank, homeSquare());
	}
}
