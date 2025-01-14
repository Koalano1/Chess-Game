package com.cchess.game.room;

import com.cchess.game.user.UserDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public RoomDto toDto(Room room) {
        return RoomDto.builder()
                .roomId(room.getId())
                .players(room.getPlayers()
                        .stream()
                        .map(this::convertUserDto)
                        .collect(Collectors.toSet()))
                .currentPlayer(room.getGameState().getCurrentPlayer())
                .otherPlayer(room.getGameState().getOtherPlayer())
                .build();
    }

    private UserDto convertUserDto(UserDto userDto) {
        return UserDto.builder()
                .username(userDto.getUsername())
                .elo(userDto.getElo())
                .matchesLost(userDto.getMatchesLost())
                .matchesWon(userDto.getMatchesWon())
                .build();
    }

}
