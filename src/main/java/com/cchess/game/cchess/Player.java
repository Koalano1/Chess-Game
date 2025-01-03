package com.cchess.game.cchess;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Player {

    @NonNull
    private String username;

    @NonNull
    private Boolean isRed;
}
