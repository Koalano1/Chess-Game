package com.cchess.game.cchess.pieces;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;

public class Chariot extends Piece {
    public Chariot(boolean isRed) {
        super(
                PieceSymbol.CHARIOT.getName(),
                PieceSymbol.CHARIOT.getSymbol(),
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
        int maxRow = Math.max(rowFrom, rowTo);
        int minRow = Math.min(rowFrom, rowTo);
        int maxCol = Math.max(colFrom, colTo);
        int minCol = Math.min(colFrom, colTo);

        if (rowFrom == rowTo) {
            return isPathClearAndValid(board, rowFrom, minCol, maxCol, true, pieceAtTo);
        }
        if (colFrom == colTo) {
            return isPathClearAndValid(board, colFrom, minRow, maxRow, false, pieceAtTo);
        }
        return false;
    }

    private boolean isPathClearAndValid(Board board, int fixed, int start, int end, boolean isRowFixed, Piece pieceAtTo) {
        for (int i = start + 1; i <= end - 1; i++) {
            if ((isRowFixed ? board.getArray()[fixed][i] : board.getArray()[i][fixed]) != null) {
                return false;
            }
        }
        return pieceAtTo == null || hasDifferentColor(pieceAtTo);
    }

}
