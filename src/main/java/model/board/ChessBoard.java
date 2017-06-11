package model.board;

import static model.enums.ViewVector.LEFT;
import static model.enums.ViewVector.RIGHT;
import static model.board.views.RankViewFactory.rankView;

import java.util.ArrayList;
import java.util.List;

import model.board.views.RankView;
import model.enums.Color;
import model.enums.Rank;
import model.enums.Row;
import model.enums.ViewVector;
import model.exceptions.ConstructorArgsException;
import model.exceptions.IllegalGameEventException;
import model.piece.MovementTrackablePiece;
import model.piece.Pawn;
import model.piece.Piece;

public class ChessBoard {

	private final boolean boardIsSet;
	private final BackingMap backingMap;
	private List<GameEvent> gameEvents;

	public ChessBoard() {
		gameEvents = new ArrayList<GameEvent>();
		backingMap = new BackingMap();
		boardIsSet = false;
	}

	public boolean boardIsSet() {
		return boardIsSet;
	}

	public ChessBoard setBoardForGame() {
		ChessBoard chessBoard = new BoardSetter().setBoard();
		return new ChessBoard(chessBoard.gameEvents, chessBoard.backingMap,
				true);
	}

	public ChessBoard setBoardForGameInProgress() {
		guard_SetBoardForGameInProgress();
		return new ChessBoard(gameEvents, backingMap, true);
	}

	private ChessBoard(List<GameEvent> gameEvents, BackingMap backingMap,
			boolean boardIsSet) {
		if (gameEvents == null || backingMap == null) {
			throw new ConstructorArgsException(
					"Constructor does not accept null arguments!");
		}
		this.gameEvents = gameEvents;
		this.backingMap = backingMap;
		this.boardIsSet = boardIsSet;
	}

	public ChessBoard playEvent(GameEvent event) {
		return event.playEvent(this);
	}

	public Piece pieceAt(Square square) {
		return backingMap.getPieceAt(square);
	}

	public Square squareHolding(Piece piece) {
		return backingMap.getSquareHolding(piece);
	}

	public List<GameEvent> gameEvents() {
		return gameEvents;
	}

	public List<GameEvent> potentialGameEvents(Color color) {
		ArrayList<GameEvent> potentialGameEvents = new ArrayList<GameEvent>();
		List<Piece> pieces = piecesFor(color);
		for (Piece piece : pieces) {
			potentialGameEvents.addAll(piece.possibleEvents(this));
		}
		return potentialGameEvents;
	}

	public List<Piece> piecesFor(Color color) {
		return backingMap.pieces(color);
	}

	ChessBoard put(PutEvent put) {
		guard(put);
		return new ChessBoard(eventsList(put), backingMap(put), boardIsSet);
	}

	private void guard(PutEvent put) {
		guard_BoardMustNotBeSet();
		if (backingMap.isOccupied(put.target())) {
			throw new IllegalArgumentException(
					"Attempted to put a piece on an occupied square!");
		}
	}

	ChessBoard move(MoveEvent move) {
		guard(move);
		return new ChessBoard(eventsList(move), backingMap(move), boardIsSet);
	}

	ChessBoard enPassantEnable(EnPassantEnableEvent e) {
		return new ChessBoard(eventsList(e), backingMap(e), boardIsSet);
	}

	ChessBoard enPassantDisable(EnPassantDisableEvent e) {
		return new ChessBoard(eventsList(e), backingMap(e), boardIsSet);
	}

	private void guard(MoveEvent move) {
		guard_BoardMustBeSet();
		if (isNotLegalMove(move)) {
			throw new IllegalGameEventException("Move is Illegal!");
		}
	}

	ChessBoard capture(CaptureEvent capture) {
		guard(capture);
		return new ChessBoard(eventsList(capture), backingMap(capture),
				boardIsSet);
	}

	private void guard(CaptureEvent capture) {
		guard_BoardMustBeSet();
		if (isNotLegalCapture(capture)) {
			throw new IllegalGameEventException("Capture is Illegal!");
		}
	}

	public ChessBoard capture(EnPassantCaptureEvent enPassantCapture) {
		guard(enPassantCapture);

		ChessBoard chessBoard = new ChessBoard(eventsList(enPassantCapture),
				backingMap(enPassantCapture), boardIsSet);

		return chessBoard;
	}

	private void guard(EnPassantCaptureEvent enPassantCapture) {
		guard_BoardMustBeSet();
		if (isNotLegalCapture(enPassantCapture)) {
			throw new IllegalGameEventException("Capture is Illegal!");
		}
	}

	public ChessBoard promote(PromoteEvent promote) {
		guard(promote);
		return new ChessBoard(eventsList(promote), backingMap(promote),
				boardIsSet);
	}

	private void guard(PromoteEvent promote) {
		guard_BoardMustBeSet();
		Piece p = pieceAt(promote.source());
		if (p.rank() != Rank.Pawn) {
			throw new IllegalGameEventException("Only Pawns may be Promoted!");
		}
		Row row = promote.source().row();
		if (row != Row.R2 && row != Row.R7) {
			throw new IllegalArgumentException(
					"Pawn must be in row before edge of board to qualify for Promotion!");
		}
	}

	public ChessBoard castle(CastleEvent castle) {
		guard(castle);
		return new ChessBoard(eventsList(castle), backingMap(castle),
				boardIsSet);
	}

	private void guard(CastleEvent castle) {
		guard_BoardMustBeSet();

		MovementTrackablePiece king = king(castle);
		MovementTrackablePiece rook = rook(castle);

		checkMovement(king, rook);

		if (!king.moveToSquares(this).contains(castle.getKingTarget())) {
			throw new IllegalGameEventException("Move is Illegal!!");
		}
		if (!rook.moveToSquares(this).contains(castle.rookTarget())) {
			throw new IllegalGameEventException("Move is Illegal!!");
		}
	}

	private void checkMovement(MovementTrackablePiece king,
			MovementTrackablePiece rook) {
		if (king.hasMoved()) {
			throw new IllegalGameEventException(
					"Kings may not castle after moving!");
		}
		if (rook.hasMoved()) {
			throw new IllegalGameEventException(
					"Rooks may not castle after moving!");
		}
	}

	private MovementTrackablePiece rook(CastleEvent castle) {
		Piece p = pieceAt(castle.rookSource());
		if (p == null) {
			throw new IllegalGameEventException("Rook not found!");
		}
		if (p.rank() != Rank.Rook) {
			throw new IllegalGameEventException(
					"Kings may only castle with a Rook!");
		}
		MovementTrackablePiece rook = (MovementTrackablePiece) p;
		return rook;
	}

	private MovementTrackablePiece king(CastleEvent castle) {
		Piece p = pieceAt(castle.source());
		if (p == null) {
			throw new IllegalGameEventException("King not found!");
		}
		if (p == null || p.rank() != Rank.King) {
			throw new IllegalGameEventException("Only Kings may Castle!");
		}
		MovementTrackablePiece king = (MovementTrackablePiece) p;
		return king;
	}

	ChessBoard remove(RemoveEvent remove) {
		guard(remove);
		return new ChessBoard(eventsList(remove), backingMap(remove),
				boardIsSet);
	}

	private void guard(RemoveEvent remove) {
		guard_BoardMustNotBeSet();
		if (backingMap.isNotOccupied(remove.source())) {
			throw new IllegalArgumentException(
					"Attempted to remove a piece from an empty square!");
		}
	}

	private BackingMap backingMap(GameEvent event) {
		switch (event.type()) {
		case PUT:
			PutEvent pe = (PutEvent) event;
			return backingMap.put(pe.target(), pe.piece());
		case REMOVE:
			return backingMap.remove(event.source());
		case MOVE:
			return backingMap.move(event.source(), event.target());
		case CAPTURE:
			CaptureEvent ce = (CaptureEvent) event;
			return backingMap.capture(ce.source(), ce.target());
		case EN_PASSANT_CAPTURE:
			EnPassantCaptureEvent epce = (EnPassantCaptureEvent) event;
			return backingMap.enPassantCapture(epce.source(), epce.target(),
					epce.captureSquare());
		case PROMOTE:
			PromoteEvent pr = (PromoteEvent) event;
			return backingMap.promote(pr.source(), pr.promoteTo());
		case CASTLE:
			CastleEvent ca = (CastleEvent) event;
			return backingMap.castle(ca.source(), ca.target(), ca.rookSource(),
					ca.rookTarget());
		case EN_PASSANT_ENABLE:
			EnPassantEnableEvent ep = ((EnPassantEnableEvent) event);
			return backingMap.enPassantEnable(ep.source());
		case EN_PASSANT_DISABLE:
			EnPassantDisableEvent dis = ((EnPassantDisableEvent) event);
			return backingMap.enPassantDisable(dis.source());
		default:
			throw new IllegalArgumentException("Event Type: " + event.type()
					+ " Not Supported!");
		}
	}

	private List<GameEvent> eventsList(GameEvent event) {
		List<GameEvent> afterGameEvents = new ArrayList<GameEvent>(gameEvents);
		afterGameEvents.add(event);

		return afterGameEvents;
	}

	private void guard_BoardMustBeSet() {
		if (!boardIsSet) {
			throw new IllegalStateException(
					"Attempted to move or capture a piece on the board before it was set!");
		}
	}

	private void guard_BoardMustNotBeSet() {
		if (boardIsSet) {
			throw new IllegalStateException(
					"Attempted to put a piece on the board after it was set!");
		}
	}

	private void guard_SetBoardForGameInProgress() {
		if (backingMap.isEmpty()) {
			throw new IllegalStateException("Attempted to set an empty board!");
		}
		if (boardIsSet) {
			throw new IllegalStateException(
					"Attempted to set an already set board!");
		}
	}

	private boolean isLegalMove(MoveEvent event) {
		Piece piece = pieceAt(event.source());
		if (piece == null) {
			return false;
		}
		return moveToSquares(piece).contains(event.target());
	}

	private boolean isNotLegalMove(MoveEvent move) {
		return !isLegalMove(move);
	}

	private boolean isLegalCapture(CaptureEvent event) {
		Piece piece = pieceAt(event.source());
		return attackAtSquares(piece).squaresHoldingPiecesAttacked().contains(
				event.target());
	}

	private boolean isNotLegalCapture(CaptureEvent capture) {
		return !isLegalCapture(capture);
	}

	private boolean isLegalCapture(EnPassantCaptureEvent event) {
		Piece piece = pieceAt(event.source());
		return attackAtSquares(piece).squaresHoldingPiecesAttacked().contains(
				event.captureSquare());
	}

	private boolean isNotLegalCapture(EnPassantCaptureEvent enPassantCapture) {
		return !isLegalCapture(enPassantCapture);
	}

	private List<Square> moveToSquares(Piece piece) {
		return rankView(piece, this).moveToSquares();
	}

	private RankView attackAtSquares(Piece piece) {
		return rankView(piece, this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((backingMap == null) ? 0 : backingMap.hashCode());
		result = prime * result + (boardIsSet ? 1231 : 1237);
		result = prime * result
				+ ((gameEvents == null) ? 0 : gameEvents.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChessBoard other = (ChessBoard) obj;
		if (backingMap == null) {
			if (other.backingMap != null)
				return false;
		} else if (!backingMap.equals(other.backingMap))
			return false;
		if (boardIsSet != other.boardIsSet)
			return false;
		if (gameEvents == null) {
			if (other.gameEvents != null)
				return false;
		} else if (!gameEvents.equals(other.gameEvents))
			return false;
		return true;
	}

}
