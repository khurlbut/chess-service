package service;

import static model.piece.PieceFactory.newPiece;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static service.BoardStateTranslator.boardToString;
import static service.BoardStateTranslator.compressedIdOf;
import model.board.ChessBoard;
import model.board.Square;
import model.enums.Color;
import model.enums.Column;
import model.enums.Rank;
import model.enums.Row;
import model.piece.Piece;

import org.junit.Test;

public class BoardStateTranslatorTest {

	private static final String BLACK_QUEEN = "bQ";
	private static final String SQUARES_FOR_SET_BOARD = "wR,wN,wB,wQ,wK,wB,wN,wR,wP,wP,wP,wP,wP,wP,wP,wP,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,bP,bP,bP,bP,bP,bP,bP,bP,bR,bN,bB,bQ,bK,bB,bN,bR";

	@Test
	public void translate_set_board_to_simple_string() {
		ChessBoard board = new ChessBoard().setBoardForGame();
		String squares = boardToString(board);
		assertThat(squares, equalTo(SQUARES_FOR_SET_BOARD));
	}

	@Test
	public void translator_can_retrieve_the_compressed_id_from_a_piece() {
		Piece q = newPiece(Color.BLACK, Rank.QUEEN,
				new Square(Column.A, Row.R8));
		assertThat(compressedIdOf(q), equalTo(BLACK_QUEEN));
	}

}
