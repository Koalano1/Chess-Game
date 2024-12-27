package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

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

        Piece pieceAtTo = board.getBoard()[rowTo][colTo];

        // Nếu điểm đầu và điểm đích nằm trên cùng hàng ngang (cùng row -> "horizontal")
        if (rowFrom == rowTo) {

            int piecesBetween = piecesBetween(board, "horizontal", from, to);

            // Nếu không có quân nào ở giữa, và có quân nằm ở vị trí đích -> false
            if (piecesBetween == 0 && pieceAtTo != null)
                return false;

            if (piecesBetween == 0)
                return true;

            // Nếu có một quân ở giữa, và quân đích là khác màu -> true
            if (piecesBetween == 1 && pieceAtTo != null && pieceAtTo.isRed() != this.isRed)
                return true;

            // Nếu có lớn hơn 1 quân nằm giữa -> false
            return false;
        }

        // Nếu điểm đầu và điểm đích nằm trên một hàng dọc (cùng col -> "vertical")
        if (colFrom == colTo) {
            int piecesBetween = piecesBetween(board, "vertical", from, to);

            // Nếu không có quân nào ở giữa, và có quân ở điểm đich -> false
            if (piecesBetween == 0 && pieceAtTo != null)
                return false;

            if (piecesBetween == 0)
                return true;

            // Nếu có 1 quân nằm giữa, và quân ở đích khác màu -> true
            if (piecesBetween == 1 && pieceAtTo != null && pieceAtTo.isRed() != this.isRed)
                return true;

            // Các trường hợp còn lại -> false
            return false;

        }

        // Điểm đầu và điểm đích không cùng row hoặc col
        return false;
    }

    private int piecesBetween(Board board, String type, Position from, Position to) {
        Piece[][] pieces = board.getBoard();
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
