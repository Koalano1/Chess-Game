package com.cchess.game.user.mapper;

import com.cchess.game.user.UserDto;
import com.cchess.game.user.UserRegisterResponse;
import com.cchess.game.model.entities.User;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")
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
