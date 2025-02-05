package com.cchess.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorDto> handleUnprocessableEntity(BadRequestException ex) {
        BaseErrorDto errorResponse = BaseErrorDto.builder()
                .timestamp(new Date(System.currentTimeMillis()))
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameExistedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<BaseErrorDto> handleUsernameExisted(UsernameExistedException ex) {
        BaseErrorDto errorResponse = BaseErrorDto.builder()
                .timestamp(new Date(System.currentTimeMillis()))
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<BaseErrorDto> handleUnauthorized(AuthenticationException ex) {
        BaseErrorDto errorResponse = BaseErrorDto.builder()
                .timestamp(new Date(System.currentTimeMillis()))
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // get message if possible
        String message = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(message);
        } catch (IllegalArgumentException ignored) {
        }

        BaseErrorDto errorResponse = BaseErrorDto.builder()
                .timestamp(new Date(System.currentTimeMillis()))
                .message(errorCode.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BaseErrorDto> handleNotFound(NotFoundException ex) {
        BaseErrorDto errorResponse = BaseErrorDto.builder()
                .timestamp(new Date(System.currentTimeMillis()))
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OtpInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorDto> handleOtpInvalid(OtpInvalidException ex) {
        BaseErrorDto errorResponse = BaseErrorDto.builder()
                .timestamp(new Date(System.currentTimeMillis()))
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMoveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorDto> invalidMoveException(InvalidMoveException ex) {
        BaseErrorDto errorResponse = BaseErrorDto.builder()
                .timestamp(new Date(System.currentTimeMillis()))
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
