package model.board;

import static model.enums.GameEventType.EN_PASSANT_CAPTURE;
import model.enums.GameEventType;
import model.exceptions.ConstructorArgsException;
import model.piece.Piece;

public class EnPassantCaptureEvent implements GameEvent {

    private Square source;
    private Square target;
    private Square captureSquare;

    public EnPassantCaptureEvent(Square source, Square target, Square captureSquare) {
        if (source == null || target == null || captureSquare == null) {
            throw new ConstructorArgsException("Constructor does not allow null(s)!");
        }
        this.source = source;
        this.target = target;
        this.captureSquare = captureSquare;
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
        return chessBoard.capture(this);
    }

    public Square captureSquare() {
        return captureSquare;
    }

    @Override
    public String toString() {
        return "en passant capture " + source + " x " + target;
    }

    @Override
    public GameEventType type() {
        return EN_PASSANT_CAPTURE;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((captureSquare == null) ? 0 : captureSquare.hashCode());
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
        EnPassantCaptureEvent other = (EnPassantCaptureEvent) obj;
        if (captureSquare == null) {
            if (other.captureSquare != null)
                return false;
        } else if (!captureSquare.equals(other.captureSquare))
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
