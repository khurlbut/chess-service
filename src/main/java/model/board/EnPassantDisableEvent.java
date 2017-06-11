package model.board;

import model.enums.GameEventType;
import model.exceptions.ConstructorArgsException;
import static model.enums.GameEventType.EN_PASSANT_DISABLE;

public class EnPassantDisableEvent implements GameEvent {
	private Square source;

	public EnPassantDisableEvent(Square source) {
    	if (source == null || source == null){
			throw new ConstructorArgsException(
					"Constructor does not allow null(s)!");
    	}
		this.source = source;
	}

	@Override
	public Square target() {
        throw new UnsupportedOperationException("There is no target. EnPassant Disablement in place.");
	}

	@Override
	public Square source() {
		return source;
	}

	@Override
	public ChessBoard playEvent(ChessBoard chessBoard) {
		return chessBoard.enPassantDisable(this);
	}


	@Override
	public GameEventType type() {
		return EN_PASSANT_DISABLE;
	}
	
	@Override
	public String toString() {
        return "en passant disable " + source;
	}

}
