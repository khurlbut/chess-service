package model.game;

import static model.enums.Color.WHITE;
import static model.enums.Rank.KING;
import static model.game.EventBuilder.getEvent;
import static model.game.EventHandler.handleEvent;
import static service.BoardStateTranslator.boardToString;

import java.util.List;

import model.board.ChessBoard;
import model.board.GameEvent;
import model.enums.Color;
import model.exceptions.IllegalGameEventException;
import model.piece.Piece;
import service.Game;

public class GamePlayer {
	private static final String SERVICE_VERSION = "0.1.0";
	private static final int GAME_NUMBER = 1;

	private boolean gameIsOver = false;
	private ChessBoard board = null;
	private Color player = null;

	public Game getGame() {
		board = new ChessBoard().setBoardForGame();

		player = WHITE;
		gameIsOver = false;

		return new Game(SERVICE_VERSION, GAME_NUMBER, boardToString(board));
	}

	public Game takeTurn(int from, int to) {

		try {
			checkBoardState();

			GameEvent event = getEvent(from, to, board);

			checkPlayerTurn(event);

			board = makeMove(event);

			checkOpponentKing();

			player = player.opponentColor();

		} catch (IllegalGameEventException e) {
			System.out.println(e.getMessage());
		}

		return new Game(SERVICE_VERSION, GAME_NUMBER, boardToString(board));
	}

	private void checkBoardState() {
		if (board == null) {
			throw new IllegalGameEventException("Board is not set!");
		}
		if (gameIsOver) {
			throw new IllegalGameEventException("Game is over!");
		}
	}

	private void checkPlayerTurn(GameEvent event) {
		Piece p = board.pieceAt(event.source());
		if (p == null) {
			throw new IllegalGameEventException(
					"Piece not found on source square!!");
		}
		if (p.color() != player) {
			throw new IllegalGameEventException("Not your turn!");
		}
	}

	private ChessBoard makeMove(GameEvent event) {
		ChessBoard tempBoard = handleEvent(event, board);

		Piece king = findKing(player, tempBoard);
		if (check(king, tempBoard)) {
			throw new IllegalGameEventException("Move Leaves King in Check!");
		}

		return tempBoard;
	}
	
	private static final Piece findKing(Color color, ChessBoard chessBoard) {
		Piece opponentKing = null;
		List<Piece> opponentPieces = chessBoard.piecesFor(color);
		for (Piece piece : opponentPieces) {
			if (piece.rank() == KING) {
				opponentKing = piece;
				break;
			}
		}
		return opponentKing;
	}

	private static final boolean check(Piece king, ChessBoard chessBoard) {
		if (king != null && chessBoard != null) {
			return king.opponentsAttackingMe(chessBoard).size() > 0;
		}
		return false;
	}

	private void checkOpponentKing() {
		Piece opponentKing = findKing(player.opponentColor(), board);
		if (check(opponentKing, board)) {
			System.out.println("Check!");
			if (isCheckMate()) {
				System.out.println("Check Mate!");
				gameIsOver = true;
			}
		}
	}

	private boolean isCheckMate() {
		ChessBoard tempBoard = null;
		Color opponentColor = player.opponentColor();
		List<Piece> pieces = board.piecesFor(opponentColor);

		for (Piece piece : pieces) {
			List<GameEvent> events = piece.possibleEvents(board);

			for (GameEvent gameEvent : events) {
				tempBoard = EventHandler.handleEvent(gameEvent, board);

				Piece king = findKing(opponentColor, tempBoard);
				if (!check(king, tempBoard)) {
					return false;
				}
			}
		}
		return true;

	}

}
