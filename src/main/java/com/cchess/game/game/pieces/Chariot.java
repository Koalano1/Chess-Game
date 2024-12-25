package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

public class Chariot extends Piece {
    public Chariot() {
        super(
                PieceSymbol.CHARIOT.getName(),
                PieceSymbol.CHARIOT.getSymbol()
        );
    }

    @Override
    boolean isValidMove(Board board, Position from, Position to) {
        return false;
    }
}
