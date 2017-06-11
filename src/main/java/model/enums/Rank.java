package model.enums;

public enum Rank {
    PAWN("Pawn", 1), ROOK("Rook", 5), KNIGHT("Knight", 3), BISHOP("Bishop", 4), QUEEN("Queen", 9), KING("King", 20);

    private final String title;
    private short value;

    Rank(String title, int value) {
        this.title = title;
        this.value = (short) value;
    }

    public String title() {
        return title;
    }

    public short value() {
        return value;
    }

}
