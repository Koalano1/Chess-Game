package com.cchess.game.game;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.stream.events.Characters;

@Data
@AllArgsConstructor
public class Position {
    private int x;
    private int y;

    public static Position getPositionFromString(String position) {
        return new Position(
                Character.getNumericValue(position.charAt(0)),
                Character.getNumericValue(position.charAt(1))
        );
    }

    @Override
    public String toString() {
        return "Position{x=" + x + ", y=" + y + "}";
    }
}
