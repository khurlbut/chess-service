package model.board;

import static model.enums.GameEventType.MOVE;
import model.enums.GameEventType;
import model.enums.Rank;
import model.enums.Row;
import model.exceptions.ConstructorArgsException;
import model.piece.Piece;

public class MoveEvent implements GameEvent {

    private Square source;
    private Square target;

    MoveEvent(Square source, Square target) {
        if (source == null || target == null) {
            throw new ConstructorArgsException("Constructor does not allow null(s)!");
        }
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

    @Override
    public ChessBoard playEvent(ChessBoard chessBoard) {
    	Piece movingPiece = chessBoard.pieceAt(source);
    	Row targetRow = target.row();
    	
    	if (movingPiece.rank() == Rank.Pawn) { 
    		if (targetRow == Row.R1 || targetRow == Row.R8){
    			PromoteEvent promoteEvent = new PromoteEvent(source, target);
    			return chessBoard.promote(promoteEvent);
    		}
    	}
    	
        return chessBoard.move(this);
    }

    @Override
    public String toString() {
        return source + " --> " + target;
    }

    @Override
    public GameEventType type() {
        return MOVE;
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
        MoveEvent other = (MoveEvent) obj;
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
