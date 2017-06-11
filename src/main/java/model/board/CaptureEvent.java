package model.board;

import static model.board.Sugar.isPromotion;
import static model.board.Sugar.promote;
import static model.enums.GameEventType.CAPTURE;
import model.enums.GameEventType;
import model.exceptions.ConstructorArgsException;
import model.piece.Piece;

public class CaptureEvent implements GameEvent {

    private Square source;
    private Square target;
    private Piece targetedPiece;

    public CaptureEvent(Square source, Square target, Piece targetedPiece) {
        if (source == null || target == null || targetedPiece == null) {
            throw new ConstructorArgsException("Constructor does not allow null(s)!");
        }
        this.source = source;
        this.target = target;
        this.targetedPiece = targetedPiece;
    }

    @Override
    public Square target() {
        return target;
    }

    @Override
    public Square source() {
        return source;
    }

    @Override
    public ChessBoard playEvent(ChessBoard chessBoard) {
		if (isPromotion(chessBoard.pieceAt(source), target.row)) {
			chessBoard = chessBoard.promote(promote(source));
		}
        return chessBoard.capture(this);
    }

    public Piece targetedPiece() {
        return targetedPiece;
    }

    @Override
    public String toString() {
        return source + " x " + target;
    }

    @Override
    public GameEventType type() {
        return CAPTURE;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((targetedPiece == null) ? 0 : targetedPiece.hashCode());
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
        CaptureEvent other = (CaptureEvent) obj;
        if (targetedPiece == null) {
            if (other.targetedPiece != null)
                return false;
        } else if (!targetedPiece.equals(other.targetedPiece))
            return false;
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
