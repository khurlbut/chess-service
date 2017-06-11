package model.board;

import static model.board.EnPassantEnabler.enableNextMoveEnPassants;
import static model.board.Sugar.put;
import static model.enums.Color.BLACK;
import static model.enums.Color.WHITE;
import static model.enums.Column.D;
import static model.enums.Column.E;
import static model.enums.Column.F;
import static model.enums.Rank.PAWN;
import static model.enums.Row.R2;
import static model.enums.Row.R4;
import static model.piece.PieceFactory.newPiece;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.piece.Pawn;

import org.junit.Before;
import org.junit.Test;

public class EnPassantTest {

	private static final Square E2 = new Square(E, R2);
	private static final Square D4 = new Square(D, R4);
	private static final Square E4 = new Square(E, R4);
	private static final Square F4 = new Square(F, R4);

	private static final Pawn WHITE_PAWN_E = (Pawn) newPiece(WHITE, PAWN, E2);
	
	private static final Pawn BLACK_PAWN_D_4 = (Pawn) newPiece(BLACK, PAWN, D4);
	private static final Pawn BLACK_PAWN_F = (Pawn) newPiece(BLACK, PAWN, F4);
	
	private static final Pawn WHITE_PAWN_D = (Pawn) newPiece(WHITE, PAWN, D4);
	private static final Pawn WHITE_PAWN_F = (Pawn) newPiece(WHITE, PAWN, F4);
	
	private static final MoveEvent MOVE_WHITE_PAWN = new MoveEvent(E2, E4);
	
	private ChessBoard chessBoard;
	
	@Before
	public void setUp() {
		chessBoard = new ChessBoard().playEvent(put(WHITE_PAWN_E));
	}
	
	@Test
	public void en_passant_enables_opponent_pawn_left() {
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_D_4)).setBoardForGameInProgress();
		
		Pawn blackPawn = (Pawn) chessBoard.pieceAt(D4);
		assertFalse(blackPawn.hasEnPassantCapture(E4));
		
		chessBoard = chessBoard.playEvent(MOVE_WHITE_PAWN);
		chessBoard = enableNextMoveEnPassants(MOVE_WHITE_PAWN, chessBoard);

		blackPawn = (Pawn) chessBoard.pieceAt(D4);
		assertTrue(blackPawn.hasEnPassantCapture(E4));
	}
	
	@Test
	public void en_passant_enables_opponent_pawn_right() {
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_F)).setBoardForGameInProgress();
		
		Pawn blackPawn = (Pawn) chessBoard.pieceAt(F4);
		assertFalse(blackPawn.hasEnPassantCapture(E4));
		
		chessBoard = chessBoard.playEvent(MOVE_WHITE_PAWN);
		chessBoard = enableNextMoveEnPassants(MOVE_WHITE_PAWN, chessBoard);
		
		blackPawn = (Pawn) chessBoard.pieceAt(F4);
		assertTrue(blackPawn.hasEnPassantCapture(E4));
	}
	
	@Test
	public void en_passant_enables_both_opponent_pawns() {
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_D_4));
		chessBoard = chessBoard.playEvent(put(BLACK_PAWN_F));
		chessBoard = chessBoard.setBoardForGameInProgress();
		
		Pawn blackPawnD = (Pawn) chessBoard.pieceAt(D4);
		Pawn blackPawnF = (Pawn) chessBoard.pieceAt(F4);
		
		assertFalse(blackPawnD.hasEnPassantCapture(E4));
		assertFalse(blackPawnF.hasEnPassantCapture(E4));
		
		chessBoard = chessBoard.playEvent(MOVE_WHITE_PAWN);
		chessBoard = enableNextMoveEnPassants(MOVE_WHITE_PAWN, chessBoard);
		
		blackPawnD = (Pawn) chessBoard.pieceAt(D4);
		blackPawnF = (Pawn) chessBoard.pieceAt(F4);
		
		assertTrue(blackPawnD.hasEnPassantCapture(E4));
		assertTrue(blackPawnF.hasEnPassantCapture(E4));
	}
	
	@Test
	public void en_passant_does_not_enable_teammates() {
		chessBoard = chessBoard.playEvent(put(WHITE_PAWN_D));
		chessBoard = chessBoard.playEvent(put(WHITE_PAWN_F));
		chessBoard = chessBoard.setBoardForGameInProgress();
		
		Pawn whitePawnD = (Pawn) chessBoard.pieceAt(D4);
		Pawn whitePawnF = (Pawn) chessBoard.pieceAt(F4);
		
		assertFalse(whitePawnD.hasEnPassantCapture());
		assertFalse(whitePawnF.hasEnPassantCapture());
		
		chessBoard = chessBoard.playEvent(MOVE_WHITE_PAWN);
		
		whitePawnD = (Pawn) chessBoard.pieceAt(D4);
		whitePawnF = (Pawn) chessBoard.pieceAt(F4);
		
		assertFalse(whitePawnD.hasEnPassantCapture(E4));
		assertFalse(whitePawnF.hasEnPassantCapture(E4));
	}

}
