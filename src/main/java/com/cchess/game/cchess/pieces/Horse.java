package com.cchess.game.cchess.pieces;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;

public class Horse extends Piece {
    public Horse(boolean isRed) {
        super(
                PieceSymbol.HORSE.getName(),
                PieceSymbol.HORSE.getSymbol(),
                isRed
        );
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int rowFrom = from.getRow();
        int colFrom = from.getCol();
        int rowTo = to.getRow();
        int colTo = to.getCol();

        Piece[][] pieces = board.getBoard();
        
        boolean cond1 = (colFrom - colTo == 2 && Math.abs(rowFrom - rowTo) == 1
            && pieces[rowFrom][colFrom - 1] == null && pieces[rowTo][colFrom - 1] == null);

        boolean cond2 = (colFrom - colTo == -2 && Math.abs(rowFrom - rowTo) == 1
            && pieces[rowFrom][colFrom + 1] == null && pieces[rowTo][colFrom + 1] == null);

        boolean cond3 = (Math.abs(colFrom - colTo) == 1 && rowFrom - rowTo == 2
            && pieces[rowFrom - 1][colFrom] == null && pieces[rowFrom - 1][colTo] == null);

        boolean cond4 = (Math.abs(colFrom - colTo) == 1 && rowFrom - rowTo == -2
            && pieces[rowFrom + 1][colFrom] == null && pieces[rowFrom + 1][colTo] == null);

        if (!cond1 && !cond2 && !cond3 && !cond4) return false;
        Piece pieceAtTo = board.getBoard()[rowTo][colTo];
        if (pieceAtTo == null) return true;
        return this.isRed != pieceAtTo.isRed();
    }
}
