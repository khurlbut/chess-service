package model.board.views;

import static model.board.Sugar.hasMoved;
import static model.board.Sugar.isCollaborator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.board.BoardPosition;
import model.board.ChessBoard;
import model.board.Square;
import model.enums.Color;
import model.enums.ViewVector;
import model.piece.MovementTrackablePiece;
import model.piece.Piece;

public class PawnView implements RankView {

    private static final ViewVector[] UP_ATTACKS = { ViewVector.RIGHT_UP, ViewVector.LEFT_UP };
    private static final ViewVector[] DOWN_ATTACKS = { ViewVector.RIGHT_DOWN, ViewVector.LEFT_DOWN };
    private final Color viewColor;
    private final ViewVector pawnDirection;

    private final List<Square> moveToSquares;
    private final List<Square> squaresHoldingPiecesAttacked;
    private final List<Square> squaresHoldingPiecesDefended;
    private final ChessBoard chessBoard;
    private final Square viewPoint;

    public PawnView(Color viewColor, BoardPosition boardPosition) {

        this.chessBoard = boardPosition.chessBoard();
        viewPoint = boardPosition.square();
        this.viewColor = viewColor;

        this.pawnDirection = pawnDirection();

        moveToSquares = new ArrayList<Square>();
        squaresHoldingPiecesAttacked = new ArrayList<Square>();
        squaresHoldingPiecesDefended = new ArrayList<Square>();

        addSquaresToLists();
    }

    private void addSquaresToLists() {
        addMoveToSquares();

        List<Square> threatenedSquares = threatenedSquares();
        for (Square threatenedSquare : threatenedSquares) {
            Piece otherPiece = chessBoard.pieceAt(threatenedSquare);

            if (otherPiece != null) {
                if (isCollaborator(viewColor, otherPiece)) {
                    squaresHoldingPiecesDefended.add(threatenedSquare);
                } else {
                    squaresHoldingPiecesAttacked.add(threatenedSquare);
                }
            }

        }
    }

    private void addMoveToSquares() {
        Square oneStep = viewPoint.neighbor(pawnDirection);
        if (chessBoard.pieceAt(oneStep) == null) {
            moveToSquares.add(oneStep);

            Piece thisPawn = chessBoard.pieceAt(viewPoint);
            if (!((MovementTrackablePiece) thisPawn).hasMoved()) {
                Square twoSteps = oneStep.neighbor(pawnDirection);
                if (chessBoard.pieceAt(twoSteps) == null) {
                    moveToSquares.add(twoSteps);
                }
            }
        }
    }

    private ViewVector pawnDirection() {
        return viewColor.equals(Color.WHITE) ? ViewVector.UP : ViewVector.DOWN;
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
        Square leftAttack = viewPoint.neighbor(pawnAttacks()[0]);
        Square rightAttack = viewPoint.neighbor(pawnAttacks()[1]);

        if (leftAttack != null && rightAttack != null) {
            return Arrays.asList(leftAttack, rightAttack);
        }
        if (leftAttack != null) {
            return Arrays.asList(leftAttack);
        }
        if (rightAttack != null) {
            return Arrays.asList(rightAttack);
        }

        return Collections.emptyList();
    }

    private ViewVector[] pawnAttacks() {
        if (ViewVector.UP.equals(pawnDirection)) {
            return UP_ATTACKS;
        } else {
            return DOWN_ATTACKS;
        }
    }

}
