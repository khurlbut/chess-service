package model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.enums.Color;
import model.piece.Piece;

import com.google.common.collect.ImmutableMap;

final class BackingMap {

    private ImmutableMap<Square, Piece> backingMap = null;
    private ImmutableMap<Piece, Square> invertedBackingMap = null;

    BackingMap() {
        this.backingMap = new ImmutableMap.Builder<Square, Piece>().build();
        this.invertedBackingMap = new ImmutableMap.Builder<Piece, Square>().build();
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

    BackingMap remove(Square source) {
        validateRemoveArgs(source);
        return new BackingMap(newBackingMapAfterRemove(source));
    }

    private BackingMap(Map<Square, Piece> mutableMap) {
        this.backingMap = new ImmutableMap.Builder<Square, Piece>().putAll(mutableMap).build();
        this.invertedBackingMap = new ImmutableMap.Builder<Piece, Square>().putAll(invertMap()).build();
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

    private Map<Square, Piece> newBackingMapAfterMoveOrCapture(Square source, Square target) {
        Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);
        map.put(target, map.get(source));
        map.remove(source);
        return map;
    }

    private Map<Square, Piece> newBackingMapAfterRemove(Square square) {
        Map<Square, Piece> map = new HashMap<Square, Piece>(backingMap);
        map.remove(square);
        return map;
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
            throw new IllegalArgumentException("Attempted to put a piece on an occupied square!");
        }
        if (isOnBoard(piece)) {
            throw new IllegalArgumentException("Attempted to put the same piece on the board twice!");
        }
    }

    private void validateMoveArgs(Square source, Square target) {
        if (isNotOccupied(source)) {
            throw new IllegalArgumentException("Attempted to move from an empty square!");
        }
        if (isOccupied(target)) {
            throw new IllegalArgumentException("Attempted to move a piece on an occupied square!");
        }
    }

    private void validateCaptureArgs(Square source, Square target) {
        if (isNotOccupied(source)) {
            throw new IllegalArgumentException("Attempted to replace from an empty square!");
        }
        if (isNotOccupied(target)) {
            throw new IllegalArgumentException("Attempted to replace on an empty square!");
        }
    }

    private void validateRemoveArgs(Square source) {
        if (isNotOccupied(source)) {
            throw new IllegalArgumentException("Attempted to remove a piece on an empty square!");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((backingMap == null) ? 0 : backingMap.hashCode());
        result = prime * result + ((invertedBackingMap == null) ? 0 : invertedBackingMap.hashCode());
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
