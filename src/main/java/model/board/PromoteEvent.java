package model.board;

import static model.enums.GameEventType.PROMOTE;
import model.enums.GameEventType;
import model.enums.Rank;

public class PromoteEvent extends MoveEvent implements GameEvent {

    private final Square source;
    private final Square target;
    private final Rank promoteTo = null;

    PromoteEvent(Square source, Square target) {
    	super(source, target);

        this.source = source;
        this.target = target;
    }

    @Override
    public Square source() {
        return source;
    }

    @Override
    public Square target() {
        return target;
    }
    
    public Rank promoteTo() {
    	return promoteTo;
    }

    @Override
    public ChessBoard playEvent(ChessBoard chessBoard) {
        return chessBoard.promote(this);
    }

    @Override
    public String toString() {
        return source + " --> " + target;
    }

    @Override
    public GameEventType type() {
        return PROMOTE;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((source == null) ? 0 : source.hashCode());
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
        PromoteEvent other = (PromoteEvent) obj;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        if (target == null) {
            if (other.target != null)
                return false;
        } else if (!target.equals(other.target))
            return false;
        return true;
    }

}
