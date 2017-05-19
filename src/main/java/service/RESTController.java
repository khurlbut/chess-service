package service;
import static model.board.BoardStateTranslator.boardToString;
import model.board.ChessBoard;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTController {
	private static final String SERVICE_VERSION = "0.0.1";
	private static final int GAME_NUMBER = 1;

	private ChessBoard board = new ChessBoard().setBoardForGame();
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/game", method = RequestMethod.GET)
	public Game getGame() {
		return new Game(SERVICE_VERSION, GAME_NUMBER, boardToString(board));
	}
}
