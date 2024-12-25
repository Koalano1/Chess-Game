package com.cchess.game.game.pieces;

import com.cchess.game.game.Board;
import com.cchess.game.game.Position;
import com.cchess.game.game.utils.PieceUtils;
import lombok.Data;

@Data
public abstract class Piece {

    protected final String name;

    protected final String symbol;

    public Piece(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    protected boolean isRed;

    abstract boolean isValidMove(Board board, Position from, Position to);

    public Piece clone(Piece piece) {
        Piece newPiece = PieceUtils.getPieceInstanceFromName(piece.getName());
        newPiece.setRed(isRed);
        return newPiece;
    }
}
