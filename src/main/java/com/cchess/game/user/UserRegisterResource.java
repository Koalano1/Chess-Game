package com.cchess.game.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@RequestMapping("/api/v1/users")
public interface UserRegisterResource {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    UserRegisterResponse register(
            @Valid @RequestBody UserRegisterRequest request
    );

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    String verifyOtp(
            @RequestBody Map<String, String> requestBody
    );

}
