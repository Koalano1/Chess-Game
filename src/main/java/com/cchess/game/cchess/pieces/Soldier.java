package com.cchess.game.cchess.pieces;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;

public class Soldier extends Piece {
    public Soldier(boolean isRed) {
        super(
                PieceSymbol.SOLDIER.getName(),
                PieceSymbol.SOLDIER.getSymbol(),
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
            if (rowTo > rowFrom)
                return false;
        } else {
            if (rowTo < rowFrom)
                return false;
        }

        if ((rowFrom != rowTo) && (colFrom != colTo))
            return false;

        if (isRed) {
            if ((rowTo == 5 || rowTo == 6) && (rowFrom == rowTo))
                return false;
        } else {
            if ((rowTo == 3 || rowTo == 4) && (rowFrom == rowTo))
                return false;
        }

        return (Math.abs(rowFrom - rowTo) == 1 || Math.abs(colFrom - colTo) == 1)
                && (pieceAtTo == null || hasDifferentColor(pieceAtTo));
    }
}
