package com.cchess.game.cchess.matches;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Player {

    @NonNull
    private String username;

    @Builder.Default
    private Boolean isRed = Boolean.FALSE;

    @Builder.Default
    private Boolean isReady = Boolean.FALSE;

}
