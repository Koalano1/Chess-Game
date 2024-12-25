package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

public class General extends Piece {

    public General() {
        super(
                PieceSymbol.GENERAL.getName(),
                PieceSymbol.GENERAL.getSymbol()
        );
    }

    @Override
    boolean isValidMove(Board board, Position from, Position to) {
        return false;
    }
}
