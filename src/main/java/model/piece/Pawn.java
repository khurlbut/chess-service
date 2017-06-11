package model.piece;

import model.board.Square;
import model.enums.Color;
import model.enums.Rank;

public class Pawn extends MovementTrackablePiece {
	private final Square enPassantCaptureSquare;

	Pawn(Color color, Rank rank, Square homeSquare, boolean hasMoved) {
		super(color, rank, homeSquare, hasMoved);
		this.enPassantCaptureSquare = null;
	}
	
	Pawn(Pawn pawn, Square enPassantCaptureSquare) {
		super(pawn.color(), pawn.rank(), pawn.homeSquare(), pawn.hasMoved());
		this.enPassantCaptureSquare = enPassantCaptureSquare;
	}

	public boolean hasEnPassantCapture(Square captureSquare) {
		return captureSquare.equals(enPassantCaptureSquare);
	}

	public boolean hasEnPassantCapture() {
		return enPassantCaptureSquare != null;
	}

}
