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

        Piece[][] pieces = board.getBoard();
        Piece pieceAtTo = pieces[rowTo][colTo];

        return (Math.abs(rowFrom - rowTo) == 2 && Math.abs(colFrom - colTo) == 2)
                && (pieceAtTo == null || pieceAtTo.isRed() != this.isRed);
    }
}
