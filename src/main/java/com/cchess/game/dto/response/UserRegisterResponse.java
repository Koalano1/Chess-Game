package com.cchess.game.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterResponse {

    private String username;

    private String email;

}
