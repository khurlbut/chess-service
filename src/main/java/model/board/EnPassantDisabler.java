package model.board;

import static model.board.Sugar.enPassantDisable;

import java.util.ArrayList;
import java.util.List;

import model.enums.Color;
import model.enums.Rank;
import model.piece.Pawn;
import model.piece.Piece;

public final class EnPassantDisabler {

	public static final ChessBoard disablePreviousMoveEnPassants(ChessBoard chessBoard, Color colorToDisalble) {
		GameEvent event;
		List<Piece> pieces = chessBoard.piecesFor(colorToDisalble);

		for (Pawn pawn : enPassantEnabledPawns(pieces)) {
			event = enPassantDisable(chessBoard.squareHolding(pawn));
			chessBoard = event.playEvent(chessBoard);
		}
		
		return chessBoard;
	}

	private static final List<Pawn> enPassantEnabledPawns(List<Piece> pieces) {
		List<Pawn> enPassantEnabledPawns = new ArrayList<Pawn>();

		for (Piece p : pieces) {
			if (p.rank() == Rank.PAWN) {
				Pawn pawn = (Pawn) p;
				if (pawn.hasEnPassantCapture()) {
					enPassantEnabledPawns.add(pawn);
				}
			}
		}
		return enPassantEnabledPawns;
	}

}
