package model.enums;

public enum TravelDistance {
    ONE_UNIT_SQUARE(true), EDGE_OF_BOARD(false);

    TravelDistance(boolean oneUnitSquare) {
        this.oneUnitSquare = oneUnitSquare;
    }

    private boolean oneUnitSquare;

    public boolean edgeOfBoard() {
        return !oneUnitSquare;
    }

}
