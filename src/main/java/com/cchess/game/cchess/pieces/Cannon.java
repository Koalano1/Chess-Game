package com.cchess.game.cchess.pieces;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;

public class Cannon extends Piece {

    public Cannon(boolean isRed) {
        super(
                PieceSymbol.CANNON.getName(),
                PieceSymbol.CANNON.getSymbol(),
                isRed
        );
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int rowFrom = from.getRow();
        int colFrom = from.getCol();
        int rowTo = to.getRow();
        int colTo = to.getCol();

        Piece pieceAtTo = board.getArray()[rowTo][colTo];
        if (rowFrom == rowTo) {
            boolean hasPiecesBetweenHorizontal = board.hasPiecesBetweenHorizontal(from, to);
            if (!hasPiecesBetweenHorizontal && pieceAtTo != null)
                return false;

            if (!hasPiecesBetweenHorizontal)
                return true;

            return pieceAtTo != null && hasDifferentColor(pieceAtTo);
        }

        if (colFrom == colTo) {
            boolean hasPiecesBetweenVertical = board.hasPiecesBetweenVertical(from, to);
            if (!hasPiecesBetweenVertical && pieceAtTo != null)
                return false;

            if (!hasPiecesBetweenVertical)
                return true;

            return pieceAtTo != null && hasDifferentColor(pieceAtTo);
        }
        return false;
    }

}
