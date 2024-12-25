package com.cchess.game.security.auth;

import com.cchess.game.dto.request.LoginRequest;
import com.cchess.game.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse authenticate(LoginRequest request);
}
