package model.board;

import static model.enums.GameEventType.EN_PASSANT_ENABLE;
import model.enums.GameEventType;
import model.exceptions.ConstructorArgsException;

public class EnPassantEnableEvent implements GameEvent {

	private Square source;
	private Square target;

	public EnPassantEnableEvent(Square source, Square target) {
    	if (source == null || source == null){
			throw new ConstructorArgsException(
					"Constructor does not allow null(s)!");
    	}
		this.source = source;
		this.target = target;
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
