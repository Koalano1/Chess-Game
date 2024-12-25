package com.cchess.game.game.pieces;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PieceSymbol {

    GENERAL("GE", "帥"),
    ADVISOR("AD", "仕"),
    ELEPHANT("EL", "相"),
    CHARIOT("CH", "車"),
    HORSE("HO", "馬"),
    CANNON("CA", "炮"),
    SOLDIER("SO", "兵");

    private final String name;
    private final String symbol;

    @Override
    public String toString() {
        return name + "(" + symbol + ")";
    }

}
