package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

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
        return false;
    }
}
