package model.piece;

import model.board.Square;
import model.enums.Color;
import model.enums.Rank;

public class Pawn extends MovementTrackablePiece {
	private final boolean hasEnPassantCapture;

	Pawn(Color color, Rank rank, Square homeSquare, boolean hasMoved) {
		super(color, rank, homeSquare, hasMoved);
		this.hasEnPassantCapture = false;
	}
	
	Pawn(Pawn pawn, boolean hasEnPassantCapture) {
		super(pawn.color(), pawn.rank(), pawn.homeSquare(), pawn.hasMoved());
		this.hasEnPassantCapture = hasEnPassantCapture;
	}

	public boolean hasEnPassantCapture() {
		return hasEnPassantCapture;
	}

}
