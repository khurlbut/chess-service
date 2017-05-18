package service.chess;

public class Game {

	private final String version;
	private final int gameNumber;
	private final String squares;

	public Game(String version, int gameNumber, String squares) {
		this.version = version;
		this.gameNumber = gameNumber;
		this.squares = squares;
	}

	public String getVersion() {
		return version;
	}

	public int getGameNumber() {
		return gameNumber;
	}

	public String getSquares() {
		return squares;
	}

}
