package service;

import model.game.GamePlayer;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {
	private static final GamePlayer gamePlayer = new GamePlayer();

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/game", method = RequestMethod.GET)
	public Game getGame() {

		return gamePlayer.getGame();

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public Game makeMove(@RequestParam("from") String from,
			@RequestParam("to") String to) {

		try {

			int fromInt = Integer.parseInt(from);
			int toInt = Integer.parseInt(to);

			return gamePlayer.takeTurn(fromInt, toInt);

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"'to' and 'from' parameters must be integers between 0 and 63!");
		}

	}
}
