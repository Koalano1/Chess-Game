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

        Piece pieceAtTo = board.getBoard()[rowTo][colTo];
        int maxRow = Math.max(rowFrom, rowTo);
        int minRow = Math.min(rowFrom, rowTo);
        int maxCol = Math.max(colFrom, colTo);
        int minCol = Math.min(colFrom, colTo);

        if (rowFrom == rowTo) {
            for (int col = minCol + 1; col <= maxCol - 1; col++) {
                if (board.getBoard()[rowFrom][col] != null) {
                    return false;
                }
            }
            if (pieceAtTo == null) {
                return true;
            }
            return pieceAtTo.isRed() != this.isRed;
        }
        if (colFrom == colTo) {
            for (int row = minRow + 1; row <= maxRow - 1; row++) {
                if (board.getBoard()[row][colFrom] != null) {
                    return false;
                }
            }
            if (pieceAtTo == null) {
                return true;
            }
            return pieceAtTo.isRed() != this.isRed;
        }
        return false;
    }

}
