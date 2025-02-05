package com.cchess.game.util;

import com.cchess.game.cchess.base.Board;
import com.cchess.game.cchess.base.Position;
import com.cchess.game.cchess.pieces.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PieceUtils {

    public static Position getGeneralPosition(Board board, boolean isRed) {
        for (int col = 3; col <= 5; col++) {
            if (isRed) {
                for (int row = 7; row <= 9; row++) {
                    if (board.getArray()[row][col] == null) continue;
                    if (board.getArray()[row][col].getName().equals("GE")) {
                        return new Position(row, col);
                    }
                }
            } else {
                for (int row = 0; row <= 2; row++) {
                    if (board.getArray()[row][col] == null) continue;
                    if (board.getArray()[row][col].getName().equals("GE")) {
                        return new Position(row, col);
                    }
                }
            }
        }
        return new Position();
    }

}
