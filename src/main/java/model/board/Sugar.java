package model.board;

import static model.piece.PieceFactory.newPiece;

import java.util.Arrays;
import java.util.List;

import model.enums.Color;
import model.enums.Column;
import model.enums.Rank;
import model.enums.Row;
import model.enums.ViewVector;
import model.piece.MovementTrackablePiece;
import model.piece.Pawn;
import model.piece.Piece;

public final class Sugar {

    public static PutEvent put(Piece piece) {
        return new PutEvent(piece);
    }

    public static PutEvent put(Color color, Rank rank, Square square) {
        return new PutEvent(newPiece(color, rank, square));
    }

    public static MoveEvent move(Square source, Square target) {
        return new MoveEvent(source, target);
    }

    public static CaptureEvent capture(Piece attacker, Piece target, ChessBoard board) {
        Square attackingSquare = board.squareHolding(attacker);
        Square targetSquare = board.squareHolding(target);
        return capture(attackingSquare, targetSquare, target);
    }

    public static CaptureEvent capture(Square attackingSquare, Square targetSquare, Piece target) {
        return new CaptureEvent(attackingSquare, targetSquare, target);
    }
    
    public static EnPassantCaptureEvent enPassantCapture(Square attackingSquare, Square targetSquare, Square captureSquare) {
    	return new EnPassantCaptureEvent(attackingSquare, targetSquare, captureSquare);
    }

    public static PromoteEvent promote(Square source) {
        return new PromoteEvent(source, Rank.QUEEN);
    }

	public static boolean isCastle(Piece piece, Square source, Square target) {
		if (piece.rank() == Rank.KING) {
			MovementTrackablePiece king = (MovementTrackablePiece) piece;
			if (!king.hasMoved()) {
				if (source.row() == target.row()) {
					if (target.col() == Column.C || target.col() == Column.G) {
						return true;
					}
				}
			}
		}
		return false;
	}

	static CastleEvent castle(Square source, Square target) {
		Square rookSquare = findRookSquare(target);
		Square rookTarget = findRookTarget(rookSquare);
		return new CastleEvent(source, target,
				rookSquare, rookTarget);
	}
	
	private static Square findRookTarget(Square rookSquare) {
		if (rookSquare.col() == Column.A) {
			return square(Column.D, rookSquare.row());
		}
		return square(Column.F, rookSquare.row());
	}

	private static Square findRookSquare(Square target) {
		if (target.col() == Column.C) {
			return square(Column.A, target.row());
		}
		return square(Column.H, target.row());
	}

	public static boolean isEnPassant(Piece movingPiece, Square source, Square target) {
		if (movingPiece instanceof Pawn) {
			Pawn pawn = (Pawn) movingPiece;
			if (pawn.hasEnPassantCapture() && (source.col() != target.col())) {
				return true;
			}
		}
		return false;
	}
	
	public static GameEvent enPassantDisable(Square square) {
		return new EnPassantDisableEvent(square);
	}
	
	public static Square enPassanteTarget(Square source, Square target) {
		Square captureTarget = null;
		
		if (source.col().ordinal() < target.col().ordinal()) {
			captureTarget = source.neighbor(ViewVector.RIGHT);
		} else if (source.col().ordinal() > target.col().ordinal()) {
			captureTarget = source.neighbor(ViewVector.LEFT);
		} else {
			throw new IllegalArgumentException("Invalid En-Passant Attempt!");
		}
		
		return captureTarget;
	}

    static RemoveEvent remove(Square square) {
        return new RemoveEvent(square);
    }

    public static Square square(Column column, Row row) {
        return new Square(column, row);
    }

    public static BoardPosition position(Column column, Row row, ChessBoard board) {
        return new BoardPosition(square(column, row), board);
    }

    public static ChessBoard play(List<GameEvent> events, ChessBoard board) {
        for (GameEvent event : events) {
            board = board.playEvent(event);
        }
        return board;
    }

    public static List<GameEvent> eventList(GameEvent... events) {
        return Arrays.asList(events);
    }

    public static boolean isCollaborator(Color color, Piece otherPiece) {
        return otherPiece != null && otherPiece.color().equals(color);
    }
    
    public static boolean isPromotion(Piece movingPiece, Row targetRow) {
		if (movingPiece.rank() == Rank.PAWN) {
			if (targetRow == Row.R1 || targetRow == Row.R8) {
				return true;
			}
		}
		return false;
    }

}
