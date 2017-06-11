package service;

import model.board.Square;
import model.enums.Column;
import model.enums.Row;


public final class SquareTranslator {
	private static final int COLUMNS_PER_ROW = 8;
	
	public static final Square boardNumberToSquare(String boardNumber) {
		if (boardNumber == null) {
			throw new IllegalArgumentException("Paramaters must not be null!");
		}
		
		int n = new Integer(boardNumber);
		Column col = column(n % COLUMNS_PER_ROW);
		Row row = row(n / COLUMNS_PER_ROW);
		
		if (col == null || row == null) {
			throw new IllegalArgumentException("Invalid Board Number " +boardNumber+ "!");
		}
		
		return new Square(col, row);
	}

	private static Row row(int row) {
		return Row.values()[row];
	}

	private static Column column(int col) {
		return Column.valueOf(String.valueOf((char) (col + 65)));
	}

}
