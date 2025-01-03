package com.cchess.game.auth;

import com.cchess.game.security.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthResource {

    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> authenticate(LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @Override
    public ResponseEntity<User> authenticatedUser() {
        return ResponseEntity.ok((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
