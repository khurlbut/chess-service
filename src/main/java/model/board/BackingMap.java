package model.board;

import static model.piece.PieceFactory.newPiece;
import static model.piece.PieceFactory.newEnPassantEnabledPawn;
import static model.piece.PieceFactory.newEnPassantDisabledPawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.enums.Color;
import model.enums.Rank;
import model.piece.MovementTrackablePiece;
import model.piece.Pawn;
import model.piece.Piece;

import com.google.common.collect.ImmutableMap;

final class BackingMap {

	private ImmutableMap<Square, Piece> backingMap = null;
	private ImmutableMap<Piece, Square> invertedBackingMap = null;

	BackingMap() {
		this.backingMap = new ImmutableMap.Builder<Square, Piece>().build();
		this.invertedBackingMap = new ImmutableMap.Builder<Piece, Square>()
				.build();
	}

	Piece getPieceAt(Square square) {
		return backingMap.get(square);
	}

	Square getSquareHolding(Piece piece) {
		return invertedBackingMap.get(piece);
	}

	List<Piece> pieces(Color color) {
		List<Piece> pieces = new ArrayList<Piece>();

		for (Piece piece : invertedBackingMap.keySet()) {
			if (piece.color().equals(color)) {
				pieces.add(piece);
			}
		}

		return pieces;
	}

	BackingMap put(Square square, Piece piece) {
		validatePutArgs(square, piece);
		return new BackingMap(newBackingMapAfterPut(square, piece));
	}

	BackingMap move(Square source, Square target) {
		validateMoveArgs(source, target);
		return new BackingMap(newBackingMapAfterMoveOrCapture(source, target));
	}

	BackingMap capture(Square source, Square target) {
		validateCaptureArgs(source, target);
		return new BackingMap(newBackingMapAfterMoveOrCapture(source, target));
	}

	BackingMap enPassantCapture(Square source, Square target,
			Square captureSquare) {
		validateCaptureArgs(source, target, captureSquare);
		return new BackingMap(newBackingMapAfterEnPassant(source, target, captureSquare));
	}

	BackingMap promote(Square source, Rank promoteTo) {
		if (isNotOccupied(source)) {
			throw new IllegalArgumentException(
					"Attempted to Promote on an empty square!");
		}
		if (promoteTo == null) {
			promoteTo = Rank.QUEEN;
		}

		return new BackingMap(newBackingMapAfterPromotion(source, promoteTo));
	}

	public BackingMap castle(Square source, Square target, Square rookSource,
			Square rookTarget) {
		validateMoveArgs(source, target);
		validateMoveArgs(rookSource, rookTarget);
		
		return new BackingMap(newBackingMapAfterCastle(source, target,
				rookSource, rookTarget));
	}

	public BackingMap enPassantEnable(Square source, Square target) {
		if (isNotOccupied(source)) {
			throw new IllegalArgumentException(
					"Attempted to enable En Passant on an empty source!");
		}
		if (isNotOccupied(target)) {
			throw new IllegalArgumentException(
					"Attempted to enable En Passant on an empty target!");
		}

		return new BackingMap(newBackingMapAfterEnPassantEnabled(source, target));
	}

	public BackingMap enPassantDisable(Square source) {
		if (isNotOccupied(source)) {
			throw new IllegalArgumentException(
					"Attempted to enable En Passant on an empty square!");
		}

		return new BackingMap(newBackingMapAfterEnPassantDisabled(source));
	}

	BackingMap remove(Square source) {
		validateRemoveArgs(source);
		return new BackingMap(newBackingMapAfterRemove(source));
	}

	private BackingMap(Map<Square, Piece> mutableMap) {
		this.backingMap = new ImmutableMap.Builder<Square, Piece>().putAll(
				mutableMap).build();
		this.invertedBackingMap = new ImmutableMap.Builder<Piece, Square>()
				.putAll(invertMap()).build();
	}

	private Map<Piece, Square> invertMap() {
		Map<Piece, Square> invertedMap = new HashMap<Piece, Square>();
		for (Square square : backingMap.keySet()) {
			invertedMap.put(backingMap.get(square), square);
		}
		return invertedMap;
	}

	private Map<Square, Piece> newBackingMapAfterPut(Square square, Piece piece) {
		Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);
		map.put(square, piece);
		return map;
	}

	private Map<Square, Piece> newBackingMapAfterMoveOrCapture(Square source,
			Square target) {
		Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);

		map.put(target, trackMovement(map.get(source)));
		map.remove(source);
		return map;
	}
	
	private Map<Square, Piece> newBackingMapAfterEnPassant(Square source,
			Square target, Square capturedSquare) {
		Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);
		
		map.put(target, trackMovement(map.get(source)));
		map.remove(capturedSquare);
		map.remove(source);
		return map;
	}

	private Map<Square, Piece> newBackingMapAfterPromotion(Square source,
			Rank promoteTo) {
		Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);
		Piece p = getPieceAt(source);

		map.remove(source);
		map.put(source, newPiece(p.color(), promoteTo, p.homeSquare()));
		return map;
	}

	private Map<Square, Piece> newBackingMapAfterCastle(Square source,
			Square target, Square rookSource, Square rookTarget) {
		Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);

		Piece king = map.get(source);
		Piece rook = map.get(rookSource);

		map.put(target, king);
		map.put(rookTarget, rook);
		
		map.remove(source);
		map.remove(rookSource);

		return map;
	}

	private Map<Square, Piece> newBackingMapAfterEnPassantEnabled(Square source, Square target) {
		Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);
		Pawn p = (Pawn) getPieceAt(source);

		map.remove(source);
		map.put(source, newEnPassantEnabledPawn(p, target));
		return map;
	}

	private Map<Square, Piece> newBackingMapAfterEnPassantDisabled(Square source) {
		Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);
		Pawn p = (Pawn) getPieceAt(source);

		map.remove(source);
		map.put(source, newEnPassantDisabledPawn(p));
		return map;
	}

	private Map<Square, Piece> newBackingMapAfterRemove(Square square) {
		Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);
		map.remove(square);
		return map;
	}

	private Piece trackMovement(Piece piece) {

		if (pieceTracksMovement(piece) && pieceHasNotMoved(piece)) {
			piece = newPiece((MovementTrackablePiece) piece);
		}

		return piece;
	}

	private boolean pieceTracksMovement(Piece p) {
		return p instanceof MovementTrackablePiece;
	}

	private boolean pieceHasNotMoved(Piece p) {
		return !((MovementTrackablePiece) p).hasMoved();
	}

	private boolean isOnBoard(Piece piece) {
		if (getSquareHolding(piece) != null) {
			return true;
		}
		return false;
	}

	boolean isOccupied(Square square) {
		return backingMap.containsKey(square);
	}

	boolean isNotOccupied(Square target) {
		return !isOccupied(target);
	}

	boolean isEmpty() {
		return backingMap.isEmpty();
	}

	private void validatePutArgs(Square target, Piece piece) {
		if (isOccupied(target)) {
			throw new IllegalArgumentException(
					"Attempted to put a piece on an occupied square!");
		}
		if (isOnBoard(piece)) {
			throw new IllegalArgumentException(
					"Attempted to put the same piece on the board twice!");
		}
	}

	private void validateMoveArgs(Square source, Square target) {
		if (isNotOccupied(source)) {
			throw new IllegalArgumentException(
					"Attempted to move from an empty square!");
		}
		if (isOccupied(target)) {
			throw new IllegalArgumentException(
					"Attempted to move a piece on an occupied square!");
		}
	}

	private void validateCaptureArgs(Square source, Square target) {
		if (isNotOccupied(source)) {
			throw new IllegalArgumentException(
					"Attempted to replace from an empty square!");
		}
		if (isNotOccupied(target)) {
			throw new IllegalArgumentException(
					"Attempted to replace on an empty square!");
		}
	}
	
	private void validateCaptureArgs(Square source, Square target, Square captureSquare) {
		if (isNotOccupied(source)) {
			throw new IllegalArgumentException(
					"Attempted to en passant from an empty square!");
		}
		if (isOccupied(target)) {
			throw new IllegalArgumentException(
					"Attempted to en passant to an occupied square!");
		}
		if (isNotOccupied(captureSquare)) {
			throw new IllegalArgumentException(
					"Attempted to en passant capture an empty square!");
		}
	}

	private void validateRemoveArgs(Square source) {
		if (isNotOccupied(source)) {
			throw new IllegalArgumentException(
					"Attempted to remove a piece on an empty square!");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((backingMap == null) ? 0 : backingMap.hashCode());
		result = prime
				* result
				+ ((invertedBackingMap == null) ? 0 : invertedBackingMap
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BackingMap other = (BackingMap) obj;
		if (backingMap == null) {
			if (other.backingMap != null)
				return false;
		} else if (!backingMap.equals(other.backingMap))
			return false;
		if (invertedBackingMap == null) {
			if (other.invertedBackingMap != null)
				return false;
		} else if (!invertedBackingMap.equals(other.invertedBackingMap))
			return false;
		return true;
	}

}
