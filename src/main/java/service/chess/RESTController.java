package service.chess;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTController {
	private static final String SERVICE_VERSION = "0.0.1";
	private static final int GAME_NUMBER = 1;
	private static final String SQUARES = "'wR','wN','wB','wQ','wK','wB','wN','wR','wP','wP','wP','wP','wP','wP','wP','wP','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','bP','bP','bP','bP','bP','bP','bP','bP','bR','bN','bB','bQ','bK','bB','bN','bR'";

	@RequestMapping(value = "/game", method = RequestMethod.GET)
	public Game getGame() {
		return new Game(SERVICE_VERSION, GAME_NUMBER, SQUARES);
	}
}
