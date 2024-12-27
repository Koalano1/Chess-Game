package com.cchess.game.game;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }


    public static Position getPositionFromString(String position) {
        return new Position(
                Character.getNumericValue(position.charAt(0)),
                Character.getNumericValue(position.charAt(1))
        );
    }

    @Override
    public String toString() {
        return "Position {row=" + row + ", col=" + col + "}";
    }
}
