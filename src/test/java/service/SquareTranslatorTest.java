package service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static service.SquareTranslator.boardNumberToSquare;
import model.board.Square;
import model.enums.Column;
import model.enums.Row;

import org.junit.Test;

public class SquareTranslatorTest {

	@Test
	public void board_number_0_translates_to_A_1() {
		Square a_1 = new Square(Column.A, Row.R1);
		assertThat(boardNumberToSquare(0), equalTo(a_1));
	}

	@Test
	public void board_number_63_translates_to_H_8() {
		Square h_8 = new Square(Column.H, Row.R8);
		assertThat(boardNumberToSquare(63), equalTo(h_8));
	}

	@Test
	public void board_number_27_translates_to_D_4() {
		Square d_4 = new Square(Column.D, Row.R4);
		assertThat(boardNumberToSquare(27), equalTo(d_4));
	}

}
