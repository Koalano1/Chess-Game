package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

public class Chariot extends Piece {
    public Chariot(boolean isRed) {
        super(
                PieceSymbol.CHARIOT.getName(),
                PieceSymbol.CHARIOT.getSymbol(),
                isRed
        );
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        return false;
    }
}
