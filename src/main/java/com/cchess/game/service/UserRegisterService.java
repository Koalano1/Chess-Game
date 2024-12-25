package com.cchess.game.service;

import com.cchess.game.dto.request.UserRegisterRequest;
import com.cchess.game.dto.response.UserRegisterResponse;

public interface UserRegisterService {
    UserRegisterResponse register(UserRegisterRequest request);

    void verifyUser(String email);
}
