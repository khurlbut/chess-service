package model.board.views;

import java.util.List;

import model.board.Square;

public interface RankView {

    List<Square> moveToSquares();

    List<Square> squaresHoldingPiecesAttacked();

    List<Square> squaresHoldingPiecesDefended();

    List<Square> threatenedSquares();

    Square viewPoint();

}
