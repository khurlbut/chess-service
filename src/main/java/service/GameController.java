package service;

import static model.board.Sugar.findKing;
import static model.board.Sugar.isCheck;
import static model.enums.Rank.KING;
import static model.game.EventBuilder.getEvent;
import static model.game.EventHandler.handleEvent;
import static service.BoardStateTranslator.boardToString;

import java.util.List;

import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.Square;
import model.enums.Color;
import model.exceptions.IllegalGameEventException;
import model.game.EventHandler;
import model.piece.Piece;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
	private static final String SERVICE_VERSION = "0.0.1";
	private static final int GAME_NUMBER = 1;

	private ChessBoard board = null;
	private boolean gameIsOver = false;
	private Color movingColor = Color.WHITE;

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/game", method = RequestMethod.GET)
	public Game getGame() {

		board = new ChessBoard().setBoardForGame();
		movingColor = Color.WHITE;
		gameIsOver = false;

		return new Game(SERVICE_VERSION, GAME_NUMBER, boardToString(board));
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public Game makeMove(@RequestParam("from") String from,
			@RequestParam("to") String to) {
		
		ChessBoard newBoard = board;

		try {
			if (board == null) {
				throw new IllegalGameEventException("Board is not set!");
			}
			if (gameIsOver) {
				throw new IllegalGameEventException("Game is over!");
			}

			GameEvent event = getEvent(from, to, board);

			checkColor(event.source());

			newBoard = handleEvent(event, board);
			if (isCheck(findKing(movingColor, newBoard), newBoard)) {
				throw new IllegalGameEventException("Move Leaves King in Check!");
			}
			
			board = newBoard;
			
			Piece opponentKing = findOpponentKing();

			if (isCheck(opponentKing, board)) {
				System.out.println("Check!");
				if (isCheckMate()) {
					System.out.println("Check Mate!");
					gameIsOver = true;
				}
			}

			movingColor = movingColor.opponentColor();

		} catch (IllegalGameEventException e) {
			System.out.println(e.getMessage());
		}

		return new Game(SERVICE_VERSION, GAME_NUMBER, boardToString(board));

	}

	private boolean isCheckMate() {
		ChessBoard b = null;
		List<Piece> pieces = board.piecesFor(movingColor.opponentColor());
		for (Piece piece : pieces) {
			List<GameEvent> events = piece.possibleEvents(board);
			for (GameEvent gameEvent : events) {
				b = EventHandler.handleEvent(gameEvent, board);
				Piece k = findOpponentKing(b);
				if (!model.board.Sugar.isCheck(k, b)) {
					return false;
				}
			}
		}
		return true;

	}

	private Piece findOpponentKing() {
		return findOpponentKing(board);
	}

	private Piece findOpponentKing(ChessBoard b) {
		Piece opponentKing = null;
		List<Piece> opponentPieces = b.piecesFor(movingColor.opponentColor());
		for (Piece piece : opponentPieces) {
			if (piece.rank() == KING) {
				opponentKing = piece;
				break;
			}
		}
		return opponentKing;
	}

	private void checkColor(Square fromSquare) {
		Piece p = board.pieceAt(fromSquare);
		if (p == null) {
			throw new IllegalGameEventException(
					"Piece not found on source square!!");
		}
		if (p.color() != movingColor) {
			throw new IllegalGameEventException("Not your turn!");
		}
	}

}
