package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

public class Soldier extends Piece {
    public Soldier(boolean isRed) {
        super(
                PieceSymbol.SOLDIER.getName(),
                PieceSymbol.SOLDIER.getSymbol(),
                isRed
        );
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        return false;
    }
}
