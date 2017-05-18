package model.board.views;

import model.board.BoardPosition;
import model.board.ChessBoard;
import model.board.Square;
import model.enums.Color;
import model.enums.Rank;
import model.piece.Piece;

public class RankViewFactory {

    public static RankView rankView(Piece piece, ChessBoard chessBoard) {
        return newRankView(piece, chessBoard);
    }

    private static RankView newRankView(Piece piece, ChessBoard chessBoard) {
        Rank rank = piece.rank();
        Color color = piece.color();
        Square viewPoint = chessBoard.squareHolding(piece);
        BoardPosition position = new BoardPosition(viewPoint, chessBoard);

        switch (rank) {
            case Pawn:
                return new PawnView(color, position);
            case Rook:
                return new RookView(color, position);
            case Knight:
                return new KnightView(color, position);
            case Bishop:
                return new BishopView(color, position);
            case Queen:
                return new QueenView(color, position);
            case King:
                return new KingView(color, position);
            default:
                throw new RuntimeException("This should never happen! Rank is: " + rank);
        }
    }

}
