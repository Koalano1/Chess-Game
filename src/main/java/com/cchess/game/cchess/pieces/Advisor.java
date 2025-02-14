package com.cchess.game.cchess.pieces;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;

public class Advisor extends Piece {

    public Advisor(boolean isRed) {
        super(
                PieceSymbol.ADVISOR.getName(),
                PieceSymbol.ADVISOR.getSymbol(),
                isRed
        );
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int rowFrom = from.getRow();
        int colFrom = from.getCol();
        int rowTo = to.getRow();
        int colTo = to.getCol();

        if (board.isNotInPalace(to)) {
            return false;
        }

        if (!(Math.abs(rowFrom - rowTo) == 1 && Math.abs(colFrom - colTo) == 1)) {
            return false;
        }

        Piece pieceAtTo = board.getArray()[rowTo][colTo];
        if (pieceAtTo != null) {
            return hasDifferentColor(pieceAtTo);
        }

        return true;
    }
}
