package com.cchess.game.mapper;

import com.cchess.game.dto.response.UserRegisterResponse;
import com.cchess.game.model.entities.User;
import org.mapstruct.Mapper;
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

}
