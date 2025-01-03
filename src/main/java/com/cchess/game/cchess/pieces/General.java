package com.cchess.game.cchess.pieces;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;
import com.cchess.game.util.PieceUtils;

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

        if (board.isNotInPalace(to)) {
            return false;
        }

        Piece pieceAtTo = board.getArray()[rowTo][colTo];
        Position opponentGeneralPosition = PieceUtils.getGeneralPosition(board, !isRed);

        boolean isSameColumn = (colTo == opponentGeneralPosition.getCol());
        boolean hasPieceBetween = board.hasPiecesBetweenVertical(to, opponentGeneralPosition);
        if (isSameColumn && !hasPieceBetween) {
            return false;
        }

        boolean isAdjacent = (Math.abs(rowFrom - rowTo) + Math.abs(colFrom - colTo) == 1);
        if (pieceAtTo == null && hasPieceBetween && isAdjacent) {
            return true;
        }

        return (pieceAtTo == null || pieceAtTo.isRed() != this.isRed || hasPieceBetween) && isAdjacent;
    }

}
