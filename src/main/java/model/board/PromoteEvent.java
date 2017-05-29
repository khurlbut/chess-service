package model.board;

import static model.enums.GameEventType.PROMOTE;
import model.enums.GameEventType;
import model.enums.Rank;
import model.exceptions.ConstructorArgsException;

public class PromoteEvent implements GameEvent {

    private final Square source;
    private final Rank promoteTo;

    PromoteEvent(Square source, Rank promoteTo) {
    	if (source == null || promoteTo == null){
			throw new ConstructorArgsException(
					"Constructor does not allow null(s)!");
    	}
        this.source = source;
        this.promoteTo = promoteTo;
    }

    @Override
    public Square source() {
        return source;
    }

    @Override
    public Square target() {
        throw new UnsupportedOperationException("There is no target. Promotion happens in place.");
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
        return source + " --> " + promoteTo;
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
        result = prime * result + ((promoteTo == null) ? 0 : promoteTo.hashCode());
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
        if (promoteTo == null) {
            if (other.promoteTo != null)
                return false;
        } else if (!promoteTo.equals(other.promoteTo))
            return false;
        return true;
    }

}
