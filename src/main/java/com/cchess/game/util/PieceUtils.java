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

    public static Piece getPieceInstanceFromName(String name, boolean isRed) {
        return switch (name) {
            case "GE" -> new General(isRed);
            case "AD" -> new Advisor(isRed);
            case "EL" -> new Elephant(isRed);
            case "CH" -> new Chariot(isRed);
            case "HO" -> new Horse(isRed);
            case "CA" -> new Cannon(isRed);
            case "SO" -> new Soldier(isRed);
            default -> throw new IllegalArgumentException("Invalid piece name: " + name);
        };
    }

}
