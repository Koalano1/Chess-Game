package com.cchess.game.auth;

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
