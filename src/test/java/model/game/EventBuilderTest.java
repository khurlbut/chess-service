package model.game;

import static model.board.Sugar.put;
import static model.enums.Color.BLACK;
import static model.enums.Color.WHITE;
import static model.enums.Column.D;
import static model.enums.Column.E;
import static model.enums.Rank.Pawn;
import static model.enums.Row.R2;
import static model.enums.Row.R3;
import static model.enums.Row.R4;
import static model.enums.Row.R5;
import static model.game.EventBuilder.getEvent;
import static model.piece.PieceFactory.newPiece;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import model.board.CaptureEvent;
import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.MoveEvent;
import model.board.PutEvent;
import model.board.Square;
import model.piece.Piece;

import org.junit.Test;


public class EventBuilderTest {

	private static final String BOARD_NUMBER_AT_E2 = "12";
	private static final String BOARD_NUMBER_AT_E3 = "20";
	private static final String BOARD_NUMBER_AT_E4 = "28";
	private static final String BOARD_NUMBER_AT_D5 = "35";
	
	private static final Square E2 = new Square(E, R2);
	private static final Square E3 = new Square(E, R3);
	private static final Square E4 = new Square(E, R4);
	private static final Square D5 = new Square(D, R5);

	private static final Piece BLACK_PAWN_D5 = newPiece(BLACK, Pawn, D5);

	private static final PutEvent PUT_WHITE_PAWN_E4 = put(WHITE, Pawn, E4);
	private static final PutEvent PUT_BLACK_PAWN_D5 = put(BLACK, Pawn, D5);
	
	@Test
	public void it_creates_a_move_event() {
		MoveEvent expectedEvent = new MoveEvent(E2, E3);
		
		ChessBoard board = new ChessBoard().setBoardForGame();
		
		GameEvent event = getEvent(BOARD_NUMBER_AT_E2, BOARD_NUMBER_AT_E3, board);
		
		assertThat(event, equalTo(expectedEvent));
	}
	
	@Test
	public void it_creates_a_capture_event() {
		CaptureEvent expectedEvent = new CaptureEvent(E4, D5, BLACK_PAWN_D5);
		
		ChessBoard board = new ChessBoard();
		board = board.playEvent(PUT_WHITE_PAWN_E4);
		board = board.playEvent(PUT_BLACK_PAWN_D5);
		
		GameEvent event = getEvent(BOARD_NUMBER_AT_E4, BOARD_NUMBER_AT_D5, board);
		
		assertThat(event, equalTo(expectedEvent));
	}
	
}
