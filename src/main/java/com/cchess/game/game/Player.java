package com.cchess.game.game;

import lombok.Data;
import lombok.NonNull;

@Data
public class Player {

    @NonNull
    private String username;

    @NonNull
    private Boolean isRed;
}
