package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

public class Advisor extends Piece {

    public Advisor() {
        super(
                PieceSymbol.ADVISOR.getName(),
                PieceSymbol.ADVISOR.getSymbol()
        );
    }

    @Override
    boolean isValidMove(Board board, Position from, Position to) {
        return false;
    }
}
