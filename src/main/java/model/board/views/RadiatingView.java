package model.board.views;

import static model.board.Sugar.isCollaborator;

import java.util.ArrayList;
import java.util.List;

import model.board.BoardPosition;
import model.board.ChessBoard;
import model.board.Square;
import model.enums.Color;
import model.enums.TravelDistance;
import model.enums.ViewVector;
import model.exceptions.ConstructorArgsException;
import model.piece.Piece;

public abstract class RadiatingView implements RankView {

    protected final Color viewColor;
    private final Square viewPoint;
    private final TravelDistance travelDistance;
    private final ViewVector[] viewVectors;

    protected final ChessBoard chessBoard;

    private final List<Square> moveToSquares;
    private final List<Square> squaresHoldingPiecesAttacked;
    private final List<Square> squaresHoldingPiecesDefended;

    public RadiatingView(Color viewColor, BoardPosition boardPosition, ViewVector[] viewDirections) {
        this(viewColor, boardPosition, viewDirections, TravelDistance.EDGE_OF_BOARD);
    }

    public RadiatingView(Color viewColor, BoardPosition boardPosition, ViewVector[] viewVectors,
        TravelDistance travelDistance) {

        if (boardPosition == null || viewColor == null || viewVectors == null || travelDistance == null) {
            throw new ConstructorArgsException("Constructor does not allow null(s)!");
        }

        this.viewPoint = boardPosition.square();
        this.travelDistance = travelDistance;
        this.viewVectors = viewVectors;

        this.viewColor = viewColor;
        this.chessBoard = boardPosition.chessBoard();

        this.moveToSquares = new ArrayList<Square>();
        this.squaresHoldingPiecesDefended = new ArrayList<Square>();
        this.squaresHoldingPiecesAttacked = new ArrayList<Square>();

        addSquaresToLists();
    }

    private void addSquaresToLists() {

        for (ViewVector vv : viewVectors) {
            Square nextSquare = null;

            if ((nextSquare = viewPoint.neighbor(vv)) != null) {
                do {

                    Piece piece = chessBoard.pieceAt(nextSquare);
                    if (null != piece) {
                        if (isCollaborator(viewColor, piece)) {
                            squaresHoldingPiecesDefended.add(nextSquare);
                        } else {
                            squaresHoldingPiecesAttacked.add(nextSquare);
                        }
                        break;
                    }

                    moveToSquares.add(nextSquare);

                    if (!travelDistance.edgeOfBoard()) {
                        break;
                    }

                } while ((nextSquare = nextSquare.neighbor(vv)) != null);

            }
        }
    }

    @Override
    public Square viewPoint() {
        return viewPoint;
    }

    @Override
    public List<Square> moveToSquares() {
        return moveToSquares;
    }

    @Override
    public List<Square> squaresHoldingPiecesAttacked() {
        return squaresHoldingPiecesAttacked;
    }

    @Override
    public List<Square> squaresHoldingPiecesDefended() {
        return squaresHoldingPiecesDefended;
    }

    @Override
    public List<Square> threatenedSquares() {
        return moveToSquares;
    }

}
