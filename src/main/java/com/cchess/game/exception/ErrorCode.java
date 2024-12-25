package com.cchess.game.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_KEY(999, "Invalid key"),

    USERNAME_INVALID(1000, "Username must be at least 2 characters long"),
    PASSWORD_INVALID(1001, "Password must be at least 6 characters long"),
    EMAIL_INVALID(1002, "Invalid email address");

    private final int code;
    private final String message;
}
