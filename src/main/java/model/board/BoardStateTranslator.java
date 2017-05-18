package model.board;

import model.enums.Column;
import model.enums.Rank;
import model.enums.Row;
import model.piece.Piece;

public final class BoardStateTranslator {

    public static final String boardToString(ChessBoard board) {

        StringBuffer b = new StringBuffer(255);

        for (Row row : Row.values()) {
            for (Column col : Column.values()) {
                Piece p = board.pieceAt(new Square(col, row));
                b.append(compressedIdOf(p)).append(",");
            }
        }

        // Remove trailing comma
        b.setLength(b.length() - 1);

        return (b.toString());
    }

    public static final String compressedIdOf(Piece p) {
        if (null == p) {
            return "''";
        }
        return "'" + pieceToString(p).replace(" ", "").substring(0, 2) + "'";
    }

    private static final String pieceToString(Piece p) {
        String compressedId;
        if (Rank.Knight.equals(p.rank())) {
            // Knight is a special case since K is used for King.
            compressedId = p.toString().replace("K", "N");
        } else {
            compressedId = p.toString();
        }
        return compressedId;
    }

}
