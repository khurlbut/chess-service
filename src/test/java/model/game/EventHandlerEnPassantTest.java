package model.game;

import static model.board.Sugar.put;
import static model.enums.Color.BLACK;
import static model.enums.Color.WHITE;
import static model.enums.Column.C;
import static model.enums.Column.D;
import static model.enums.Column.E;
import static model.enums.Column.F;
import static model.enums.Rank.PAWN;
import static model.enums.Row.R2;
import static model.enums.Row.R3;
import static model.enums.Row.R4;
import static model.game.EventHandler.handleEvent;
import static model.piece.PieceFactory.newPiece;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.board.ChessBoard;
import model.board.MoveEvent;
import model.board.Square;
import model.piece.Pawn;

import org.junit.Before;
import org.junit.Test;

public class EventHandlerEnPassantTest {

	private static final Square C4 = new Square(C, R4);
	private static final Square E2 = new Square(E, R2);
	private static final Square D3 = new Square(D, R3);
	private static final Square D4 = new Square(D, R4);
	private static final Square E4 = new Square(E, R4);
	private static final Square F4 = new Square(F, R4);

	private static final Pawn WHITE_PAWN_E2 = (Pawn) newPiece(WHITE, PAWN, E2);
	private static final Pawn WHITE_PAWN_C4 = (Pawn) newPiece(WHITE, PAWN, C4);

	private static final Pawn BLACK_PAWN_D4 = (Pawn) newPiece(BLACK, PAWN, D4);
	private static final Pawn BLACK_PAWN_F4 = (Pawn) newPiece(BLACK, PAWN, F4);

	private static final MoveEvent MOVE_WHITE_PAWN_E2 = new MoveEvent(E2, E4);
	private static final MoveEvent MOVE_BLACK_PAWN_D4 = new MoveEvent(D4, D3);

	private ChessBoard chessBoard;

	@Before
	public void setUp() {
		chessBoard = new ChessBoard().playEvent(put(WHITE_PAWN_E2));
	}

	@Test
	public void en_passant_enables_opponent_pawn_left() {
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_D4))
				.setBoardForGameInProgress();

		Pawn blackPawn = (Pawn) chessBoard.pieceAt(D4);
		assertFalse(blackPawn.hasEnPassantCapture(E4));

		chessBoard = handleEvent(MOVE_WHITE_PAWN_E2, chessBoard);

		blackPawn = (Pawn) chessBoard.pieceAt(D4);
		assertTrue(blackPawn.hasEnPassantCapture(E4));
	}

	@Test
	public void en_passant_enables_opponent_pawn_right() {
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_F4))
				.setBoardForGameInProgress();

		Pawn blackPawn = (Pawn) chessBoard.pieceAt(F4);
		assertFalse(blackPawn.hasEnPassantCapture(E4));

		chessBoard = handleEvent(MOVE_WHITE_PAWN_E2, chessBoard);

		blackPawn = (Pawn) chessBoard.pieceAt(F4);
		assertTrue(blackPawn.hasEnPassantCapture(E4));
	}

	@Test
	public void en_passant_enables_both_opponent_pawns() {
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_D4));
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_F4));
		chessBoard = chessBoard.setBoardForGameInProgress();

		Pawn blackPawnD = (Pawn) chessBoard.pieceAt(D4);
		Pawn blackPawnF = (Pawn) chessBoard.pieceAt(F4);

		assertFalse(blackPawnD.hasEnPassantCapture(E4));
		assertFalse(blackPawnF.hasEnPassantCapture(E4));

		chessBoard = handleEvent(MOVE_WHITE_PAWN_E2, chessBoard);

		blackPawnD = (Pawn) chessBoard.pieceAt(D4);
		blackPawnF = (Pawn) chessBoard.pieceAt(F4);

		assertTrue(blackPawnD.hasEnPassantCapture(E4));
		assertTrue(blackPawnF.hasEnPassantCapture(E4));
	}

	@Test
	public void en_passant_is_disabled_after_enabled_pawn_moves() {
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_D4))
				.setBoardForGameInProgress();

		Pawn blackPawn = (Pawn) chessBoard.pieceAt(D4);
		assertFalse(blackPawn.hasEnPassantCapture(E4));

		chessBoard = handleEvent(MOVE_WHITE_PAWN_E2, chessBoard);

		blackPawn = (Pawn) chessBoard.pieceAt(D4);
		assertTrue(blackPawn.hasEnPassantCapture(E4));

		chessBoard = handleEvent(MOVE_BLACK_PAWN_D4, chessBoard);

		blackPawn = (Pawn) chessBoard.pieceAt(D3);
		assertFalse(blackPawn.hasEnPassantCapture(E4));
	}

	@Test
	public void en_passant_is_disabled_after_another_pawn_moves() {
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_D4));
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_F4));
		chessBoard = chessBoard.setBoardForGameInProgress();

		Pawn blackPawnD = (Pawn) chessBoard.pieceAt(D4);
		Pawn blackPawnF = (Pawn) chessBoard.pieceAt(F4);

		assertFalse(blackPawnD.hasEnPassantCapture(E4));
		assertFalse(blackPawnF.hasEnPassantCapture(E4));

		chessBoard = handleEvent(MOVE_WHITE_PAWN_E2, chessBoard);

		blackPawnD = (Pawn) chessBoard.pieceAt(D4);
		blackPawnF = (Pawn) chessBoard.pieceAt(F4);

		assertTrue(blackPawnD.hasEnPassantCapture(E4));
		assertTrue(blackPawnF.hasEnPassantCapture(E4));

		chessBoard = handleEvent(MOVE_BLACK_PAWN_D4, chessBoard);

		blackPawnD = (Pawn) chessBoard.pieceAt(D3);
		blackPawnF = (Pawn) chessBoard.pieceAt(F4);

		assertFalse(blackPawnD.hasEnPassantCapture(E4));
		assertFalse(blackPawnF.hasEnPassantCapture(E4));
	}

	@Test
	public void en_passant_can_only_capture_pawn_that_moved() {
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_F4));
		chessBoard = chessBoard.playEvent(put(WHITE_PAWN_C4));
		chessBoard = chessBoard.setBoardForGameInProgress();

		Pawn blackPawn = (Pawn) chessBoard.pieceAt(F4);
		assertFalse(blackPawn.hasEnPassantCapture(E4));

		chessBoard = handleEvent(MOVE_WHITE_PAWN_E2, chessBoard);

		blackPawn = (Pawn) chessBoard.pieceAt(F4);
		assertTrue(blackPawn.hasEnPassantCapture(MOVE_WHITE_PAWN_E2.target()));
		assertFalse(blackPawn.hasEnPassantCapture(C4));
	}

}
