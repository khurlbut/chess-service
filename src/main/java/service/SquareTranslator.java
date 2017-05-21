package service;

import model.board.Square;
import model.enums.Column;
import model.enums.Row;


public final class SquareTranslator {
	private static final int COLUMNS_PER_ROW = 8;
	
	public static final Square boardNumberToSquare(String boardNumber) {
		int n = new Integer(boardNumber);
		int row = n / COLUMNS_PER_ROW;
		int col = n % COLUMNS_PER_ROW;
		
		return new Square(column(col), row(row));
	}

	private static Row row(int row) {
		return Row.values()[row];
	}

	private static Column column(int col) {
		return Column.valueOf(String.valueOf((char) (col + 65)));
	}

}
