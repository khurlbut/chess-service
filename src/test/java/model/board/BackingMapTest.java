package model.board;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import model.enums.Color;
import model.enums.Rank;
import model.piece.MovementTrackablePiece;
import model.piece.Piece;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BackingMapTest {

	@Mock
	private Square square1;
	@Mock
	private Square square2;
	@Mock
	private Piece piece1;
	@Mock
	private Piece piece2;
	@Mock
	private MovementTrackablePiece movementTrackablePiece;

	private BackingMap backingMap;

	@Before
	public void setUp() {
		backingMap = new BackingMap();
	}

	@Test
	public void it_returns_null_when_requesting_a_piece_from_an_empty_square() {
		assertNull(backingMap.getPieceAt(square1));
	}

	@Test
	public void it_returns_the_piece_set_on_a_square() {
		backingMap = backingMap.put(square1, piece1);
		assertThat(backingMap.getPieceAt(square1), equalTo(piece1));
	}

	@Test
	public void it_returns_the_square_holding_a_piece() {
		backingMap = backingMap.put(square1, piece1);
		assertThat(backingMap.getSquareHolding(piece1), equalTo(square1));
	}

	@Test
	public void it_remembers_previous_pieces_when_a_new_piece_is_set() {
		backingMap = backingMap.put(square1, piece1);
		backingMap = backingMap.put(square2, piece2);

		assertThat(backingMap.getPieceAt(square1), equalTo(piece1));
		assertThat(backingMap.getPieceAt(square2), equalTo(piece2));
	}

	@Test
	public void it_can_move_a_piece_from_square1_to_square2() {
		backingMap = backingMap.put(square1, piece1);
		backingMap = backingMap.move(square1, square2);

		assertNull(backingMap.getPieceAt(square1));
		assertThat(backingMap.getPieceAt(square2), equalTo(piece1));
	}

	@Test
	public void it_can_move_and_update_a_movement_trackable_piece() {
		when(movementTrackablePiece.color()).thenReturn(Color.WHITE);
		when(movementTrackablePiece.rank()).thenReturn(Rank.King);
		when(movementTrackablePiece.homeSquare()).thenReturn(square1);
		when(movementTrackablePiece.hasMoved()).thenReturn(false);
		
		assertFalse(movementTrackablePiece.hasMoved());
		
		backingMap = backingMap.put(square1, movementTrackablePiece);
		backingMap = backingMap.move(square1, square2);

		assertNull(backingMap.getPieceAt(square1));

		Piece pieceAfterMove = backingMap.getPieceAt(square2);

		assertTrue(((MovementTrackablePiece) pieceAfterMove).hasMoved());
	}

	@Test
	public void it_can_replace_the_piece_on_square2_with_the_piece_on_square1() {
		backingMap = backingMap.put(square1, piece1);
		backingMap = backingMap.put(square2, piece2);

		backingMap = backingMap.capture(square1, square2);

		assertNull(backingMap.getPieceAt(square1));
		assertThat(backingMap.getPieceAt(square2), equalTo(piece1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentException_is_thrown_when_attempting_to_set_a_piece_on_a_square_that_is_occupied_by_a_new_piece() {
		backingMap = backingMap.put(square1, piece1);
		backingMap = backingMap.put(square1, piece2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentException_is_thrown_when_attempting_to_set_a_piece_on_a_square_that_is_occupied_by_the_same_piece() {
		backingMap = backingMap.put(square1, piece1);
		backingMap = backingMap.put(square1, piece1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentException_is_thrown_when_an_attemp_is_made_to_move_to_an_occupied_square() {
		backingMap = backingMap.put(square1, piece1);
		backingMap = backingMap.put(square2, piece2);
		backingMap = backingMap.move(square1, square2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentException_is_thrown_when_an_attemp_is_made_to_move_to_the_same_square() {
		backingMap = backingMap.put(square1, piece1);
		backingMap = backingMap.move(square1, square1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentException_is_thrown_when_an_attemp_is_made_to_the_same_piece_on_more_than_one_square() {
		backingMap = backingMap.put(square1, piece1);
		backingMap = backingMap.put(square2, piece1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentException_is_thrown_when_an_attempt_is_made_to_replace_on_an_empty_square() {
		backingMap = backingMap.put(square1, piece1);
		backingMap = backingMap.capture(square1, square2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentException_is_thrown_when_an_attempt_is_made_to_move_from_an_empty_square() {
		backingMap = backingMap.move(square1, square2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentException_is_thrown_when_an_attempt_is_made_to_replace_from_an_empty_square() {
		backingMap = backingMap.put(square2, piece1);
		backingMap = backingMap.capture(square1, square2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentException_is_thrown_when_an_attempt_is_made_to_remove_from_an_empty_square() {
		backingMap = backingMap.remove(square1);
	}

}
