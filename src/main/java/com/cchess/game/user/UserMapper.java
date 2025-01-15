package com.cchess.game.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserRegisterResponse toResponse(User user) {
        return UserRegisterResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .elo(user.getElo())
                .matchesLost(user.getMatchesLost().size())
                .matchesWon(user.getMatchesWon().size())
                .build();
    }
}
