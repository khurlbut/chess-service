package model.enums;

import model.board.Square;

public enum ViewVector {
	UP("up", 0, 1), DOWN("down", 0, -1), LEFT("left", -1, 0), RIGHT("right", 1,
			0), LEFT_UP("left_up", -1, 1), RIGHT_UP("right_up", 1, 1), LEFT_DOWN(
			"left_down", -1, -1), RIGHT_DOWN("right_down", 1, -1), RIGHT_UP_UP(
			"right_up_up", 1, 2), RIGHT_RIGHT_UP("right_right_up", 2, 1), RIGHT_RIGHT_DOWN(
			"right_right_down", 2, -1), RIGHT_DOWN_DOWN("right_down_down", 1,
			-2), LEFT_DOWN_DOWN("left_down_down", -1, -2), LEFT_LEFT_DOWN(
			"left_left_down", -2, -1), LEFT_LEFT_UP("left_left_up", -2, 1), LEFT_UP_UP(
			"left_up_up", -1, 2);

	private String move;
	private Integer horizontalDelta;
	private Integer verticalDelta;

	ViewVector(String move, int horizontalDelta, int verticalDelta) {
		this.move = move;
		this.horizontalDelta = horizontalDelta;
		this.verticalDelta = verticalDelta;
	}

	public int horizontalDelta() {
		return horizontalDelta;
	}

	public int verticalDelta() {
		return verticalDelta;
	}

	public static ViewVector diagonalVV(Square source, Square target) {
		ViewVector upDown = null;
		ViewVector leftRight = null;
		
		if (source.row() == target.row() || source.col() == target.col()) {
			throw new IllegalArgumentException(
					"Source and Target must be on different row and column!");
		}

		leftRight = source.col().ordinal() < target.col().ordinal() ? ViewVector.LEFT
				: ViewVector.RIGHT;
		upDown = source.row().ordinal() < target.row().ordinal() ? ViewVector.DOWN
				: ViewVector.UP;

		if (leftRight == ViewVector.RIGHT && upDown == ViewVector.UP ) {
			return ViewVector.RIGHT_UP;
		} else if ( leftRight == ViewVector.RIGHT && upDown == ViewVector.DOWN) {
			return ViewVector.RIGHT_DOWN;
		} else if (leftRight == ViewVector.LEFT && upDown == ViewVector.DOWN) {
			return ViewVector.LEFT_DOWN;
		} else {
			return ViewVector.LEFT_UP;
		}
		
	}

	@Override
	public String toString() {
		return move;
	}

}
