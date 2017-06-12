package model.board.views;

import static model.board.Sugar.eventList;
import static model.board.Sugar.move;
import static model.board.Sugar.play;
import static model.board.Sugar.position;
import static model.board.Sugar.put;
import static model.board.Sugar.square;
import static model.board.views.ViewSugar.kingView;
import static model.enums.Color.BLACK;
import static model.enums.Color.WHITE;
import static model.enums.Column.A;
import static model.enums.Column.C;
import static model.enums.Column.D;
import static model.enums.Column.E;
import static model.enums.Column.F;
import static model.enums.Column.G;
import static model.enums.Column.H;
import static model.enums.Rank.KING;
import static model.enums.Rank.ROOK;
import static model.enums.Row.R1;
import static model.enums.Row.R2;
import static model.enums.Row.R8;
import static model.piece.PieceFactory.newPiece;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.MoveEvent;
import model.board.PutEvent;
import model.board.Square;
import model.enums.Color;
import model.enums.Rank;
import model.piece.MovementTrackablePiece;

import org.junit.Before;
import org.junit.Test;

public class KingViewCastleTest {

	private KingView kingView = null;

	private static final Square E1 = square(E, R1);
	private static final Square E8 = square(E, R8);

	private static final Square A1 = square(A, R1);
	private static final Square C1 = square(C, R1);
	private static final Square D1 = square(D, R1);
	private static final Square D2 = square(D, R2);
	private static final Square E2 = square(E, R2);
	private static final Square F2 = square(F, R2);
	private static final Square F1 = square(F, R1);
	private static final Square G1 = square(G, R1);
	private static final Square H1 = square(H, R1);

	private static final Square A8 = square(A, R8);
	private static final Square C8 = square(C, R8);
	private static final Square G8 = square(G, R8);
	private static final Square H8 = square(H, R8);

	private ChessBoard chessBoard;

	@Before
	public void setUp() {
		chessBoard = new ChessBoard();
	}

	@Test
	public void it_can_castle_with_A1_rook() {
		List<Square> moveToSquares = setup_castle(WHITE, E1, A1);
		assertTrue(moveToSquares.contains(C1));
	}

	@Test
	public void it_can_castle_with_A8_rook() {
		List<Square> moveToSquares = setup_castle(BLACK, E8, A8);
		assertTrue(moveToSquares.contains(C8));
	}

	@Test
	public void it_can_castle_with_H1_rook() {
		List<Square> moveToSquares = setup_castle(WHITE, E1, H1);
		assertTrue(moveToSquares.contains(G1));
	}

	@Test
	public void it_can_castle_with_H8_rook() {
		List<Square> moveToSquares = setup_castle(BLACK, E8, H8);
		assertTrue(moveToSquares.contains(G8));
	}

	@Test
	public void it_can_not_castle_on_col_A1_rook_if_rook_is_not_on_square() {
		List<Square> moveToSquares = attempt_castle_without_rook(WHITE,
				KING, E1);
		assertFalse(moveToSquares.contains(C1));
	}

	@Test
	public void it_can_not_castle_with_A8_rook_if_rook_is_not_on_square() {
		List<Square> moveToSquares = attempt_castle_without_rook(BLACK,
				KING, E8);
		assertFalse(moveToSquares.contains(C8));
	}

	@Test
	public void it_can_not_castle_on_col_H1_rook_if_rook_is_not_on_square() {
		List<Square> moveToSquares = attempt_castle_without_rook(WHITE,
				KING, E1);
		assertFalse(moveToSquares.contains(G1));
	}

	@Test
	public void it_can_not_castle_with_H8_rook_if_rook_is_not_on_square() {
		List<Square> moveToSquares = attempt_castle_without_rook(BLACK,
				KING, E8);
		assertFalse(moveToSquares.contains(G8));
	}

	@Test
	public void it_finds_the_right_number_of_move_to_squares() {
		PutEvent put_king = put(newPiece(WHITE, KING, E1));
		PutEvent put_rook_a = put(newPiece(WHITE, ROOK, A1));
		PutEvent put_rook_h = put(newPiece(WHITE, ROOK, H1));

		List<GameEvent> putEvents = eventList(put_king, put_rook_a, put_rook_h);
		chessBoard = play(putEvents, chessBoard);
		kingView = newKingView(WHITE, E1);

		List<Square> moveToSquares = kingView.moveToSquares();
		assertThat(moveToSquares.size(), equalTo(7));

		assertTrue(moveToSquares.contains(C1));
		assertTrue(moveToSquares.contains(D1));
		assertTrue(moveToSquares.contains(D2));
		assertTrue(moveToSquares.contains(E2));
		assertTrue(moveToSquares.contains(F2));
		assertTrue(moveToSquares.contains(F1));
		assertTrue(moveToSquares.contains(G1));
	}

	@Test
	public void it_can_not_pass_through_check_when_castling() {
		PutEvent put_king = put(newPiece(WHITE, KING, E1));
		PutEvent put_rook_a = put(newPiece(WHITE, ROOK, A1));
		PutEvent put_rook_d_2 = put(newPiece(BLACK, ROOK, D2));

		List<GameEvent> putEvents = eventList(put_king, put_rook_a,
				put_rook_d_2);
		chessBoard = play(putEvents, chessBoard);
		kingView = newKingView(WHITE, E1);

		assertFalse(kingView.moveToSquares().contains(C1));
	}
	
	@Test
	public void it_can_not_castle_after_king_has_moved() {
		PutEvent put_king = put(newPiece(WHITE, KING, E1));
		PutEvent put_rook_a = put(newPiece(WHITE, ROOK, A1));
		MoveEvent move_king_over = move(E1, D1);
		MoveEvent move_king_back = move(D1, E1);
		
		List<GameEvent> putEvents = eventList(put_king, put_rook_a);
		chessBoard = play(putEvents, chessBoard);
		
		assertFalse(((MovementTrackablePiece) chessBoard.pieceAt(E1)).hasMoved());
		
		chessBoard = chessBoard.setBoardForGameInProgress();
		putEvents = eventList(move_king_over, move_king_back);
		chessBoard = play(putEvents, chessBoard);
		
		kingView = newKingView(WHITE, E1);

		assertTrue(((MovementTrackablePiece) chessBoard.pieceAt(E1)).hasMoved());

		assertFalse(kingView.moveToSquares().contains(C1));
	}
	
	@Test
	public void it_can_not_castle_after_rook_has_moved() {		
		PutEvent put_king = put(newPiece(WHITE, KING, E1));
		PutEvent put_rook_a = put(newPiece(WHITE, ROOK, A1));
		MoveEvent move_rook_over = move(A1, C1);
		MoveEvent move_rook_back = move(C1, A1);
		
		List<GameEvent> putEvents = eventList(put_king, put_rook_a);
		chessBoard = play(putEvents, chessBoard);
		
		assertFalse(((MovementTrackablePiece) chessBoard.pieceAt(A1)).hasMoved());
		
		chessBoard = chessBoard.setBoardForGameInProgress();
		putEvents = eventList(move_rook_over, move_rook_back);
		chessBoard = play(putEvents, chessBoard);
		
		kingView = newKingView(WHITE, E1);

		assertTrue(((MovementTrackablePiece) chessBoard.pieceAt(A1)).hasMoved());
		
		assertFalse(kingView.moveToSquares().contains(C1));
	}

	private List<Square> setup_castle(Color color, Square kingSquare, Square rookSquare) {
		PutEvent put_king = put(newPiece(color, KING, kingSquare));
		PutEvent put_rook = put(newPiece(color, ROOK, rookSquare));

		List<GameEvent> putEvents = eventList(put_king, put_rook);
		chessBoard = play(putEvents, chessBoard);
		kingView = newKingView(color, kingSquare);

		return kingView.moveToSquares();
	}

	private List<Square> attempt_castle_without_rook(Color color,
			Rank kingRank, Square kingSquare) {
		PutEvent put_king = put(newPiece(color, kingRank, kingSquare));

		List<GameEvent> putEvents = eventList(put_king);
		chessBoard = play(putEvents, chessBoard);
		kingView = newKingView(color, kingSquare);

		return kingView.moveToSquares();
	}

	private KingView newKingView(Color color, Square square) {
		return kingView(color, position(square.col(), square.row(), chessBoard));
	}
}
