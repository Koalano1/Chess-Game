package com.cchess.game.cchess;

import lombok.Data;
import lombok.NonNull;

@Data
public class Player {

    @NonNull
    private String username;

    @NonNull
    private Boolean isRed;
}
