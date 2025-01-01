package com.cchess.game.cchess.pieces;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;

public class Elephant extends Piece {
    public Elephant(boolean isRed) {
        super(
                PieceSymbol.ELEPHANT.getName(),
                PieceSymbol.ELEPHANT.getSymbol(),
                isRed
        );
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int rowFrom = from.getRow();
        int colFrom = from.getCol();
        int rowTo = to.getRow();
        int colTo = to.getCol();

        Piece[][] pieces = board.getArray();
        Piece pieceAtTo = pieces[rowTo][colTo];

        if (isRed) {
            if (rowTo < 5) return false;
        } else {
            if (rowTo > 4) return false;
        }

        return (Math.abs(rowFrom - rowTo) == 2 && Math.abs(colFrom - colTo) == 2)
                && (pieceAtTo == null || hasDifferentColor(pieceAtTo));
    }
}
