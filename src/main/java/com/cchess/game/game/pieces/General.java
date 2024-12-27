package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;
import com.cchess.game.game.utils.PieceUtils;

public class General extends Piece {

    public General(boolean isRed) {
        super(
                PieceSymbol.GENERAL.getName(),
                PieceSymbol.GENERAL.getSymbol(),
                isRed
        );
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int rowFrom = from.getRow();
        int colFrom = from.getCol();
        int rowTo = to.getRow();
        int colTo = to.getCol();

        if (colTo < 3 || colTo > 5 || (rowTo < 7 && rowTo > 2)) {
            return false;
        }

        Piece pieceAtTo = board.getBoard()[rowTo][colTo];
        Position opponentGeneralPosition = PieceUtils.getGeneralPosition(board, !isRed);

        boolean isSameColumn = (colTo == opponentGeneralPosition.getCol());
        boolean hasPieceBetween = hasPieceBetween(board, to, opponentGeneralPosition);
        if (isSameColumn && !hasPieceBetween) {
            return false;
        }

        boolean isAdjacent = (Math.abs(rowFrom - rowTo) + Math.abs(colFrom - colTo) == 1);
        if (pieceAtTo == null && hasPieceBetween && isAdjacent) {
            return true;
        }

        return (pieceAtTo == null || pieceAtTo.isRed() != this.isRed || hasPieceBetween) && isAdjacent;
    }

    boolean hasPieceBetween(Board board, Position to, Position opponentGeneral) {
        int lowerRow = Math.min(to.getRow(), opponentGeneral.getRow() + 1);
        int upperRow = Math.max(to.getRow(), opponentGeneral.getRow() + 1);
        for (int r = lowerRow; r <= upperRow; r++) {
            if (board.getBoard()[r][to.getCol()] != null)
                return true;
        }
        return false;
    }
}
