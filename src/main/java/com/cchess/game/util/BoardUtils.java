package com.cchess.game.util;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.pieces.Piece;

public class BoardUtils {

    public static Board clonedBoard(Board board) {
        Board clonedBoard = new Board();
        for (int i = 0; i < Board.ROW; i++) {
            for (int j = 0; j < Board.COL; j++) {
                Piece piece = board.getArray()[i][j];
                clonedBoard.getArray()[i][j] = piece;
            }
        }
        return clonedBoard;
    }

}
