package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;
import com.cchess.game.game.utils.PieceUtils;
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

    public Piece clone(Piece piece) {
        return PieceUtils.getPieceInstanceFromName(piece.getName(), isRed);
    }
}
