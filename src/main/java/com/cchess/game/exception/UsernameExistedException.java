package com.cchess.game.exception;

import lombok.Getter;

@Getter
public class UsernameExistedException extends EntityExistedException {

    public UsernameExistedException() {
        super("User with this username already exists");
    }

}
