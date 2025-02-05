package com.cchess.game.exception;

public class OtpInvalidException extends RuntimeException {
    public OtpInvalidException() {
        super("Otp is not valid.");
    }
}
