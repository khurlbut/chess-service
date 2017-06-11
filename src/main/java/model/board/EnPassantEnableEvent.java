package model.board;

import static model.enums.GameEventType.EN_PASSANT_ENABLE;
import model.enums.GameEventType;
import model.exceptions.ConstructorArgsException;

public class EnPassantEnableEvent implements GameEvent {

	private Square source;

	public EnPassantEnableEvent(Square source) {
    	if (source == null || source == null){
			throw new ConstructorArgsException(
					"Constructor does not allow null(s)!");
    	}
		this.source = source;
	}

	@Override
	public Square target() {
        throw new UnsupportedOperationException("There is no target. EnPassant Enablement in place.");
	}

	@Override
	public Square source() {
		return source;
	}

	@Override
	public ChessBoard playEvent(ChessBoard chessBoard) {
		return chessBoard.enPassantEnable(this);
	}

	@Override
	public GameEventType type() {
		return EN_PASSANT_ENABLE;
	}
	
    @Override
    public String toString() {
        return "en passant enable " + source;
    }


}
