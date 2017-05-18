package model.board;

import static model.enums.GameEventType.REMOVE;
import model.enums.GameEventType;
import model.exceptions.ConstructorArgsException;

public class RemoveEvent implements GameEvent {

    private Square source;

    RemoveEvent(Square source) {
        if (source == null) {
            throw new ConstructorArgsException("Constructor does not allow null!");
        }
        this.source = source;
    }

    @Override
    public Square target() {
        throw new UnsupportedOperationException("There is no target. A Remove goes nowhere.");
    }

    @Override
    public Square source() {
        return source;
    }

    @Override
    public ChessBoard playEvent(ChessBoard chessBoard) {
        return chessBoard.remove(this);
    }

    @Override
    public String toString() {
        return "remove " + source;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        return result;
    }

    @Override
    public GameEventType type() {
        return REMOVE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RemoveEvent other = (RemoveEvent) obj;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        return true;
    }

}
