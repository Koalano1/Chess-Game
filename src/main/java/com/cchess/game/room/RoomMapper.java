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
                        .map(user -> UserDto.builder()
                                .username(user.getUsername())
                                .elo(user.getElo())
                                .matchesLost(user.getMatchesLost())
                                .matchesWon(user.getMatchesWon())
                                .build())
                        .collect(Collectors.toSet()))
                .currentPlayer(room.getGameState().getCurrentPlayer())
                .otherPlayer(room.getGameState().getOtherPlayer())
                .build();
    }

}
