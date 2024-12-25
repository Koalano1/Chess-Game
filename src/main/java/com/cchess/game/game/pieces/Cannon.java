package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

public class Cannon extends Piece {

    public Cannon() {
        super(
                PieceSymbol.CANNON.getName(),
                PieceSymbol.CANNON.getSymbol()
        );
    }

    @Override
    boolean isValidMove(Board board, Position from, Position to) {
        return false;
    }
}
