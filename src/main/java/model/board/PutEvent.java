package model.board;

import static model.enums.GameEventType.PUT;
import model.enums.GameEventType;
import model.exceptions.ConstructorArgsException;
import model.piece.Piece;

public class PutEvent implements GameEvent {

    private Piece piece;
    private Square target;

    PutEvent(Piece piece) {
        if (piece == null) {
            throw new ConstructorArgsException("Constructor does not allow null(s)!");
        }
        this.piece = piece;
        this.target = piece.homeSquare();
    }

    @Override
    public Square target() {
        return target;
    }

    @Override
    public Square source() {
        throw new UnsupportedOperationException("There is no source. A Put comes from nothing.");
    }

    @Override
    public ChessBoard playEvent(ChessBoard chessBoard) {
        return chessBoard.put(this);
    }

    public Piece piece() {
        return piece;
    }

    @Override
    public String toString() {
        return "put " + piece + " " + target;
    }

    @Override
    public GameEventType type() {
        return PUT;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((piece == null) ? 0 : piece.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
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
        PutEvent other = (PutEvent) obj;
        if (piece == null) {
            if (other.piece != null)
                return false;
        } else if (!piece.equals(other.piece))
            return false;
        if (target == null) {
            if (other.target != null)
                return false;
        } else if (!target.equals(other.target))
            return false;
        return true;
    }

}
