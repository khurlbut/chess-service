package model.piece;

import model.board.Square;
import model.enums.Color;
import model.enums.Rank;
import model.exceptions.ConstructorArgsException;

public class PieceFactory {

    public static Piece newPiece(Color color, Rank rank, Square homeSquare) {
        if (color == null || rank == null || homeSquare == null) {
            throw new ConstructorArgsException("Arguments must not be null!");
        }
        return new Piece(color, rank, homeSquare);
    }

}
