package model.enums;

public enum ViewVector {
    UP("up", 0, 1), DOWN("down", 0, -1), LEFT("left", -1, 0), RIGHT("right", 1, 0), LEFT_UP("left_up", -1, 1), RIGHT_UP(
        "right_up", 1, 1), LEFT_DOWN("left_down", -1, -1), RIGHT_DOWN("right_down", 1, -1), RIGHT_UP_UP(
        "right_up_up", 1, 2), RIGHT_RIGHT_UP("right_right_up", 2, 1), RIGHT_RIGHT_DOWN("right_right_down", 2, -1), RIGHT_DOWN_DOWN(
        "right_down_down", 1, -2), LEFT_DOWN_DOWN("left_down_down", -1, -2), LEFT_LEFT_DOWN("left_left_down", -2, -1), LEFT_LEFT_UP(
        "left_left_up", -2, 1), LEFT_UP_UP("left_up_up", -1, 2);

    private String move;
    private Integer horizontalDelta;
    private Integer verticalDelta;

    ViewVector(String move, int horizontalDelta, int verticalDelta) {
        this.move = move;
        this.horizontalDelta = horizontalDelta;
        this.verticalDelta = verticalDelta;
    }

    public int horizontalDelta() {
        return horizontalDelta;
    }

    public int verticalDelta() {
        return verticalDelta;
    }

    @Override
    public String toString() {
        return move;
    }

}
