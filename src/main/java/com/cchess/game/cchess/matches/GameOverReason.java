package com.cchess.game.cchess.matches;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameOverReason {

    DRAW,
    TIME_UP,
    SUCCESSFUL_CHECKMATE,
    RESIGNATION;

}
