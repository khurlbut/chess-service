package service;

import model.board.Square;
import model.enums.Column;
import model.enums.Row;


public final class SquareTranslator {
	private static final int TOTAL_SQUARES = 63;
	private static final int COLUMNS_PER_ROW = 8;
	
	public static final Square boardNumberToSquare(int boardNumber) {
		if (boardNumber < 0 || boardNumber > TOTAL_SQUARES) {
			throw new IllegalArgumentException("Invalid Board Number " +boardNumber+ "!");
		}
		
		Column col = column(boardNumber % COLUMNS_PER_ROW);
		Row row = row(boardNumber / COLUMNS_PER_ROW);
		
		return new Square(col, row);
	}

	private static Row row(int row) {
		return Row.values()[row];
	}

	private static Column column(int col) {
		return Column.valueOf(String.valueOf((char) (col + 65)));
	}

}
