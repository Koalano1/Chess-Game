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
            int piecesBetween = piecesBetween(board, "horizontal", from, to);
            if (piecesBetween == 0 && pieceAtTo != null)
                return false;

            if (piecesBetween == 0)
                return true;

            if (piecesBetween == 1 && pieceAtTo != null && pieceAtTo.isRed() != this.isRed)
                return true;

            return false;
        }

        if (colFrom == colTo) {
            int piecesBetween = piecesBetween(board, "vertical", from, to);
            if (piecesBetween == 0 && pieceAtTo != null)
                return false;

            if (piecesBetween == 0)
                return true;

            if (piecesBetween == 1 && pieceAtTo != null && pieceAtTo.isRed() != this.isRed)
                return true;

            return false;
        }
        return false;
    }

    private int piecesBetween(Board board, String type, Position from, Position to) {
        Piece[][] pieces = board.getArray();
        int count = 0;
        if (type.equals("horizontal")) {
            int lowerCol = Math.min(from.getCol(), to.getCol());
            int upperCol = Math.max(from.getCol(), to.getCol());
            for (int col = lowerCol + 1; col <= upperCol - 1; col++)
                if (pieces[from.getRow()][col] != null)
                    count++;
        } else {
            int lowerRow = Math.min(from.getRow(), to.getRow());
            int upperRow = Math.max(from.getRow(), to.getRow());
            for (int row = lowerRow + 1; row <= upperRow - 1; row++)
                if (pieces[row][from.getCol()] != null)
                    count++;
        }
        return count;
    }
}
