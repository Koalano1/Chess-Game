package com.cchess.game.cchess.pieces;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;
import lombok.Data;

@Data
public abstract class Piece {

    protected final String name;

    protected final String symbol;

    protected boolean isRed;

    public Piece(String name, String symbol, boolean isRed) {
        this.name = name;
        this.symbol = symbol;
        this.isRed = isRed;
    }

    abstract public boolean isValidMove(Board board, Position from, Position to);

}
