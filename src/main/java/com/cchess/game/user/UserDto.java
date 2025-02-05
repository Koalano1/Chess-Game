package com.cchess.game.user;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NonNull
    private String username;

    @NonNull
    private Integer elo;

    private int matchesWon;

    private int matchesLost;

}
