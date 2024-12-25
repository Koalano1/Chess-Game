package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;

public class Soldier extends Piece {
    public Soldier() {
        super(
                PieceSymbol.SOLDIER.getName(),
                PieceSymbol.SOLDIER.getSymbol()
        );
    }


    @Override
    boolean isValidMove(Board board, Position from, Position to) {
        return false;
    }
}
