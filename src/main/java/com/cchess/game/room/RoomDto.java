package com.cchess.game.room;

import com.cchess.game.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RoomDto {

    private String roomId;

    private Set<UserDto> players;

}
