package model.board;

public class BoardPosition {
    private Square square;
    private ChessBoard board;

    public BoardPosition(Square square, ChessBoard board) {
        this.square = square;
        this.board = board;
    }

    public ChessBoard chessBoard() {
        return board;
    }

    public Square square() {
        return square;
    }

}
