package model.piece;

import static model.board.Sugar.capture;
import static model.board.Sugar.move;
import static model.board.views.RankViewFactory.rankView;

import java.util.ArrayList;
import java.util.List;

import model.board.ChessBoard;
import model.board.GameEvent;
import model.board.Square;
import model.board.views.RankView;
import model.enums.Color;
import model.enums.Rank;
import model.exceptions.ConstructorArgsException;

public class Piece {
    private final Rank rank;
    private final Color color;
    private final Square homeSquare;

    Piece(Color color, Rank rank, Square homeSquare) {
        if (rank == null || color == null || homeSquare == null) {
            throw new ConstructorArgsException("Constructor does not allow null(s)!");
        }
        this.rank = rank;
        this.color = color;
        this.homeSquare = homeSquare;
    }

    public Rank rank() {
        return rank;
    }

    public Color color() {
        return color;
    }

    public int points() {
        return rank.value();
    }

    public Square homeSquare() {
        return homeSquare;
    }

    public List<GameEvent> possibleEvents(ChessBoard board) {
        List<GameEvent> possibleEvents = new ArrayList<GameEvent>();

        RankView view = myView(board);

        addPossibleMoves(view, possibleEvents, board);
        addPossibleCaptures(view, possibleEvents, board);

        return possibleEvents;
    }

    private void addPossibleMoves(RankView view, List<GameEvent> possibleEvents, ChessBoard board) {
        for (Square openSquare : view.moveToSquares()) {
            possibleEvents.add(move(view.viewPoint(), openSquare));
        }
    }

    private void addPossibleCaptures(RankView view, List<GameEvent> possibleEvents, ChessBoard board) {
        for (Piece target : opponentPiecesAttacked(board, view)) {
            Square targetSquare = board.squareHolding(target);
            possibleEvents.add(capture(view.viewPoint(), targetSquare, target));
        }
    }

    public List<Piece> opponentPiecesAttacked(ChessBoard board) {
        RankView view = myView(board);
        return opponentPiecesAttacked(board, view);
    }

    private List<Piece> opponentPiecesAttacked(ChessBoard board, RankView view) {
        List<Piece> opponentPiecesAttacked = new ArrayList<Piece>();

        List<Square> squaresHoldingPiecesAttacked = view.squaresHoldingPiecesAttacked();
        for (Square square : squaresHoldingPiecesAttacked) {
            opponentPiecesAttacked.add(board.pieceAt(square));
        }
        return opponentPiecesAttacked;
    }

    public List<Piece> teammatesDefended(ChessBoard board) {
        List<Piece> teammatesDefended = new ArrayList<Piece>();

        List<Square> squaresHoldingPiecesDefended = myView(board).squaresHoldingPiecesDefended();

        for (Square square : squaresHoldingPiecesDefended) {
            teammatesDefended.add(board.pieceAt(square));
        }

        return teammatesDefended;
    }

    public List<Piece> opponentsAttackingMe(ChessBoard board) {
        List<Piece> opponentsAttackingMe = new ArrayList<Piece>();
        Square mySquare = board.squareHolding(this);

        List<Piece> opponentPieces = board.piecesFor(color.opponentColor());
        for (Piece opponentPiece : opponentPieces) {
            RankView opponentView = rankView(opponentPiece, board);

            List<Square> squaresHoldingPiecesUnderAttack = opponentView.squaresHoldingPiecesAttacked();
            if (squaresHoldingPiecesUnderAttack.contains(mySquare)) {
                opponentsAttackingMe.add(opponentPiece);
            }
        }
        return opponentsAttackingMe;

    }

    public List<Piece> teammatesDefendingMe(ChessBoard board) {
        List<Piece> teammatesDefendingMe = new ArrayList<Piece>();
        Square mySquare = board.squareHolding(this);

        List<Piece> collaborators = board.piecesFor(color);
        for (Piece collaboratorPiece : collaborators) {
            RankView collaboratorView = rankView(collaboratorPiece, board);

            List<Square> squaresHoldingPiecesDefended = collaboratorView.squaresHoldingPiecesDefended();
            if (squaresHoldingPiecesDefended.contains(mySquare)) {
                teammatesDefendingMe.add(collaboratorPiece);
            }

        }

        return teammatesDefendingMe;
    }

    public List<Square> threatenedSquares(ChessBoard board) {
        return myView(board).threatenedSquares();
    }

    public List<Square> moveToSquares(ChessBoard board) {
        return myView(board).moveToSquares();
    }

    private RankView myView(ChessBoard chessBoard) {
        return rankView(this, chessBoard);
    }

    @Override
    public String toString() {
        return color + " " + rank;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((homeSquare == null) ? 0 : homeSquare.hashCode());
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
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
        Piece other = (Piece) obj;
        if (color != other.color)
            return false;
        if (homeSquare == null) {
            if (other.homeSquare != null)
                return false;
        } else if (!homeSquare.equals(other.homeSquare))
            return false;
        if (rank != other.rank)
            return false;
        return true;
    }

}
