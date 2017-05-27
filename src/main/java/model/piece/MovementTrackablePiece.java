package model.piece;

import model.board.Square;
import model.enums.Color;
import model.enums.Rank;

public class MovementTrackablePiece extends Piece {
	private final boolean hasMoved;
	
	MovementTrackablePiece(Color color, Rank rank, Square homeSquare, boolean hasMoved) {
		super(color, rank, homeSquare);
		this.hasMoved = hasMoved;
	}

	public boolean hasMoved() {
		return hasMoved;
	}
}
