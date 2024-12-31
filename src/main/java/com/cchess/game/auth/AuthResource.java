package com.cchess.game.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthResource {

    @PostMapping
    ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest request
    );

    @GetMapping("/me")
    ResponseEntity<User> authenticatedUser();
}
