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

            if (pieceAtTo == null) {
                return !board.hasPiecesBetweenHorizontal(from, to);
            } else {
                if (hasDifferentColor(pieceAtTo)) {
                    return board.numberOfPiecesBetweenHorizontal(from, to) == 1;
                } else return false;
            }
        }

        if (colFrom == colTo) {

            if (pieceAtTo == null) {
                return !board.hasPiecesBetweenVertical(from, to);
            } else {
                if (hasDifferentColor(pieceAtTo)) {
                    return board.numberOfPiecesBetweenVertical(from, to) == 1;
                } else return false;
            }
        }
        return false;
    }

}
