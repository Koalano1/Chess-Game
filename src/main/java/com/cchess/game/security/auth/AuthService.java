package com.cchess.game.security.auth;

import com.cchess.game.auth.LoginRequest;
import com.cchess.game.auth.LoginResponse;

public interface AuthService {
    LoginResponse authenticate(LoginRequest request);
}
