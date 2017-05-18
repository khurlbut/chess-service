package model.enums;

public enum Column {
    A("A"), B("B"), C("C"), D("D"), E("E"), F("F"), G("G"), H("H");

    private final String columnName;

    Column(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return columnName;
    }

    public Column horizontalNeighbor(int cols) {
        if (this.ordinal() + cols <= (Column.values().length - 1) && (this.ordinal() + cols) >= 0) {
            return Column.values()[this.ordinal() + cols];
        }
        return null;
    }

}
