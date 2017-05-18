package model.board;

import static model.board.Sugar.square;
import model.enums.Column;
import model.enums.Row;
import model.enums.ViewVector;
import model.exceptions.ConstructorArgsException;

public class Square {

    protected final Column col;
    protected final Row row;

    public Square(Column c, Row r) {
        if (c == null || r == null) {
            throw new ConstructorArgsException("Constructor does not allow null(s)!");
        }
        col = c;
        row = r;
    }

    public Column col() {
        return col;
    }

    public Row row() {
        return row;
    }

    public Square neighbor(ViewVector vv) {
        Column c = horizontalNeighbor(vv);
        Row r = verticalNeighbor(vv);

        if (c == null || r == null) {
            return null;
        }
        return square(c, r);
    }

    private Column horizontalNeighbor(ViewVector vv) {
        return col.horizontalNeighbor(vv.horizontalDelta());
    }

    private Row verticalNeighbor(ViewVector vv) {
        return row.verticalNeighbor(vv.verticalDelta());
    }

    @Override
    public String toString() {
        return col + "_" + row;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((col == null) ? 0 : col.hashCode());
        result = prime * result + ((row == null) ? 0 : row.hashCode());
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
        Square other = (Square) obj;
        if (col != other.col)
            return false;
        if (row != other.row)
            return false;
        return true;
    }

}
