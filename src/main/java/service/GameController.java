package service;

import static model.board.EnPassantEnabler.enableNextMoveEnPassants;
import static model.board.EnPassantDisabler.disablePreviousMoveEnPassants;
import static model.board.Sugar.capture;
import static model.board.Sugar.enPassantCapture;
import static model.board.Sugar.enPassantDisable;
import static model.board.Sugar.enPassanteTarget;
import static model.board.Sugar.isEnPassant;
import static model.board.Sugar.move;
import static service.BoardStateTranslator.boardToString;
import static service.SquareTranslator.boardNumberToSquare;

import java.util.ArrayList;
import java.util.List;

import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.Square;
import model.enums.Color;
import model.enums.Rank;
import model.exceptions.IllegalGameEventException;
import model.piece.Pawn;
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

			checkColor(fromSquare);

			GameEvent event = getEvent(fromSquare, toSquare);

			board = board.playEvent(event);

			board = enableNextMoveEnPassants(event, board);
			board = disablePreviousMoveEnPassants(board, movingColor);

			movingColor = movingColor.opponentColor();

		} catch (IllegalGameEventException e) {
			System.out.println(e.getMessage());
		}

		return new Game(SERVICE_VERSION, GAME_NUMBER, boardToString(board));

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

	private GameEvent getEvent(Square fromSquare, Square toSquare) {
		Piece moving = board.pieceAt(fromSquare);
		Piece target = board.pieceAt(toSquare);

		if (isEnPassant(moving, fromSquare, toSquare)) {
			Square captureSquare = enPassanteTarget(fromSquare, toSquare);
			return enPassantCapture(fromSquare, toSquare, captureSquare);
		}
		
		if (target != null) {
			return capture(fromSquare, toSquare, target);
		}
		
		return move(fromSquare, toSquare);
	}

}
