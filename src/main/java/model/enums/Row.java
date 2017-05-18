package model.enums;

public enum Row {
    R1("1"), R2("2"), R3("3"), R4("4"), R5("5"), R6("6"), R7("7"), R8("8");

    private String rowNumber;

    Row(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String toString() {
        return rowNumber;
    }

    public Row verticalNeighbor(int rows) {
        if ((this.ordinal() + rows <= Row.values().length - 1) && (this.ordinal() + rows >= 0)) {
            return Row.values()[this.ordinal() + rows];
        }
        return null;
    }

}
