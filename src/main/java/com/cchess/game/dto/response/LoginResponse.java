package com.cchess.game.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;

    private String refreshToken;

    private boolean isAuthenticated;
}
