package service;

import static model.board.Sugar.capture;
import static model.board.Sugar.move;
import static service.BoardStateTranslator.boardToString;
import static service.SquareTranslator.boardNumberToSquare;
import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.Square;
import model.enums.Color;
import model.exceptions.IllegalGameEventException;
import model.piece.Piece;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTController {
	private static final String SERVICE_VERSION = "0.0.1";
	private static final int GAME_NUMBER = 1;

	private ChessBoard board = null;
	private Color movingColor = Color.WHITE;

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/game", method = RequestMethod.GET)
	public Game getGame() {
		board = new ChessBoard().setBoardForGame();
		movingColor = Color.WHITE;
		return new Game(SERVICE_VERSION, GAME_NUMBER, boardToString(board));
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public Game makeMove(@RequestParam("from") String from,
			@RequestParam("to") String to) {

		try {
			if (board == null) {
				board = new ChessBoard();
				throw new IllegalGameEventException("Board is not set!");
			}
			
			Square fromSquare = boardNumberToSquare(from);
			Square toSquare = boardNumberToSquare(to);
			Piece movingPiece = board.pieceAt(fromSquare);
			Piece targetPiece = board.pieceAt(toSquare);
			
			if (movingPiece.color() != movingColor) {
				throw new IllegalGameEventException("Not your turn!");
			}

			GameEvent event = getEvent(fromSquare, toSquare, targetPiece);
			board = board.playEvent(event);
			movingColor = movingColor.opponentColor();

		} catch (IllegalGameEventException e) {
			System.out.println(e.getMessage());
		}
		
		return new Game(SERVICE_VERSION, GAME_NUMBER, boardToString(board));
		
	}

	private GameEvent getEvent(Square fromSquare, Square toSquare, Piece target) {

		
		if (target != null) {
			return capture(fromSquare, toSquare, target);
		} else {
			return move(fromSquare, toSquare);
		}
	}

}
