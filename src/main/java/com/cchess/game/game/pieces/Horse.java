package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

public class Horse extends Piece {
    public Horse() {
        super(
                PieceSymbol.HORSE.getName(),
                PieceSymbol.HORSE.getSymbol()
        );
    }

    @Override
    boolean isValidMove(Board board, Position from, Position to) {
        int xFrom = from.getX();
        int yFrom = from.getY();
        int xTo = to.getX();
        int yTo = to.getY();

        Piece[][] pieces = board.getBoard();
        if (Math.abs(xFrom - xTo) == 1 && yFrom - yTo == 2
                && pieces[xFrom][yFrom - 1] == null && pieces[xTo][yTo] == null) {
            return true;
        }
        if (Math.abs(xFrom - yTo) == 1 && yFrom - yTo == -2
                && pieces[xFrom][yFrom + 1] == null && pieces[xTo][yTo] == null) {
            return true;
        }
        if (Math.abs(yFrom - yTo) == 1 && xFrom - xTo == 2
                && pieces[xFrom - 1][yFrom] == null && pieces[xTo][yTo] == null) {
            return true;
        }

        return Math.abs(yFrom - yTo) == 1 && xFrom - xTo == -2
                && pieces[xFrom + 1][yFrom] == null && pieces[xTo][yTo] == null;
    }
}
