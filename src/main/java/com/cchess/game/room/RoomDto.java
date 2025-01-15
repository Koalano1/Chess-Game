package com.cchess.game.room;

import com.cchess.game.cchess.matches.Player;
import com.cchess.game.user.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@Builder
public class RoomDto {

    @NonNull
    private String roomId;

    private Set<UserDto> players;

    private Player currentPlayer;

    private Player otherPlayer;
}
