package com.cchess.game.game.utils;

import com.cchess.game.game.pieces.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PieceUtils {

    public static Piece getPieceInstanceFromName(String name) {
        return switch (name) {
            case "GE" -> new General();
            case "AD" -> new Advisor();
            case "EL" -> new Elephant();
            case "CH" -> new Chariot();
            case "HO" -> new Horse();
            case "CA" -> new Cannon();
            case "SO" -> new Soldier();
            default -> throw new IllegalArgumentException("Invalid piece name: " + name);
        };
    }

}
