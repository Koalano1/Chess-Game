package com.cchess.game.cchess.matches;

import com.cchess.game.room.RoomDto;
import com.cchess.game.user.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameStopResponse {

    @JsonProperty("winner")
    private UserDto winner;

    @JsonProperty("loser")
    private UserDto loser;

    @JsonProperty("room")
    private RoomDto roomDto;

    @JsonProperty("type")
    private String type;

    @JsonProperty("reason")
    private GameOverReason reason;

}
