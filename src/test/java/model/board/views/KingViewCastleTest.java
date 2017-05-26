package model.board.views;

//TODO: Fix hasMoved to be a boolean based on actual movement (not the homesquare).
//TODO: Remove HomeSquare entirely? Evaluate.
//TODO: Create (and use) new event: CastleEvent to make Rook move on Castle. 

import static model.board.Sugar.eventList;
import static model.board.Sugar.play;
import static model.board.Sugar.position;
import static model.board.Sugar.put;
import static model.board.Sugar.square;
import static model.board.views.ViewSugar.kingView;
import static model.piece.PieceFactory.newPiece;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.PutEvent;
import model.board.Square;
import model.enums.Color;
import model.enums.Column;
import model.enums.Rank;
import model.enums.Row;

import org.junit.Before;
import org.junit.Test;

public class KingViewCastleTest {

	private KingView kingView = null;
	
	private final Square e_1 = square(Column.E, Row.R1);
	private final Square e_8 = square(Column.E, Row.R8);

	private final Square a_1 = square(Column.A, Row.R1);
	private final Square c_1 = square(Column.C, Row.R1);
	private final Square h_1 = square(Column.H, Row.R1);
	private final Square g_1 = square(Column.G, Row.R1);
	
	private final Square a_8 = square(Column.A, Row.R8);
	private final Square c_8 = square(Column.C, Row.R8);
	private final Square h_8 = square(Column.H, Row.R8);
	private final Square g_8 = square(Column.G, Row.R8);

	private final Square d_2 = square(Column.D, Row.R2);

	private ChessBoard chessBoard;
	
	@Before
	public void setUp() {
		chessBoard = new ChessBoard();
	}

	@Test
	public void it_finds_the_right_number_of_move_to_squares() {
		PutEvent put_king = put(newPiece(Color.WHITE, Rank.King, e_1));
		PutEvent put_rook_a = put(newPiece(Color.WHITE, Rank.Rook, a_1));
		PutEvent put_rook_h = put(newPiece(Color.WHITE, Rank.Rook, h_1));
		
		List<GameEvent> putEvents = eventList(put_king, put_rook_a, put_rook_h);
		chessBoard = play(putEvents, chessBoard);
		kingView = newKingView(Color.WHITE, e_1);
		
		assertThat(kingView.moveToSquares().size(), equalTo(7));
	}
	
	@Test
	public void it_can_not_pass_through_check_when_castling() {
		PutEvent put_king = put(newPiece(Color.WHITE, Rank.King, e_1));
		PutEvent put_rook_a = put(newPiece(Color.WHITE, Rank.Rook, a_1));
		PutEvent put_rook_d_2 = put(newPiece(Color.BLACK, Rank.Rook, d_2));
		
		List<GameEvent> putEvents = eventList(put_king, put_rook_a, put_rook_d_2);
		chessBoard = play(putEvents, chessBoard);
		kingView = newKingView(Color.WHITE, e_1);
		
		assertFalse(kingView.moveToSquares().contains(c_1));
	}
	
	@Test
	public void it_can_castle_with_A1_rook() {
		List<Square> moveToSquares = setup_castle(Color.WHITE, e_1, a_1);
		assertTrue(moveToSquares.contains(c_1));
	}

	@Test
	public void it_can_castle_with_A8_rook() {
		List<Square> moveToSquares = setup_castle(Color.BLACK, e_8, a_8);
		assertTrue(moveToSquares.contains(c_8));
	}
	
	@Test
	public void it_can_castle_with_H1_rook() {
		List<Square> moveToSquares = setup_castle(Color.WHITE, e_1, h_1);
		assertTrue(moveToSquares.contains(g_1));
	}
	
	@Test
	public void it_can_castle_with_H8_rook() {
		List<Square> moveToSquares = setup_castle(Color.BLACK, e_8, h_8);
		assertTrue(moveToSquares.contains(g_8));
	}

	@Test
	public void it_can_not_castle_on_col_A1_rook_if_rook_is_not_on_square() {
		List<Square> moveToSquares = attempt_castle_without_rook(Color.WHITE, Rank.King, e_1);
		assertFalse(moveToSquares.contains(c_1));
	}

	@Test
	public void it_can_not_castle_with_A8_rook_if_rook_is_not_on_square() {
		List<Square> moveToSquares = attempt_castle_without_rook(Color.BLACK, Rank.King, e_8);
		assertFalse(moveToSquares.contains(c_8));
	}

	@Test
	public void it_can_not_castle_on_col_H1_rook_if_rook_is_not_on_square() {
		List<Square> moveToSquares = attempt_castle_without_rook(Color.WHITE, Rank.King, e_1);
		assertFalse(moveToSquares.contains(g_1));
	}
	
	@Test
	public void it_can_not_castle_with_H8_rook_if_rook_is_not_on_square() {
		List<Square> moveToSquares = attempt_castle_without_rook(Color.BLACK, Rank.King, e_8);
		assertFalse(moveToSquares.contains(g_8));
	}

	private List<Square> setup_castle(Color color, Square kingSquare, Square rookSquare) {
		PutEvent put_king = put(newPiece(color, Rank.King, kingSquare));
		PutEvent put_rook = put(newPiece(color, Rank.Rook, rookSquare));
		
		List<GameEvent> putEvents = eventList(put_king, put_rook);
		chessBoard = play(putEvents, chessBoard);
		kingView = newKingView(color, kingSquare);
		
		return kingView.moveToSquares();
	}
	
	private List<Square> attempt_castle_without_rook(Color color, Rank kingRank, Square kingSquare) {
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
