package com.cchess.game.user;

public interface UserRegisterService {
    UserRegisterResponse register(UserRegisterRequest request);

    void verifyUser(String email);
}
