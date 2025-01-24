package com.cchess.game.security.auth;

import com.cchess.game.auth.LoginRequest;
import com.cchess.game.auth.LoginResponse;
import com.cchess.game.exception.AuthenticationException;
import com.cchess.game.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public LoginResponse authenticate(LoginRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        boolean matches = passwordEncoder.matches(request.getPassword(), userDetails.getPassword());
        if (!matches) {
            throw new AuthenticationException("Invalid username or password");
        }

        if (!userDetails.isEnabled()) throw new AuthenticationException("User is not active");

        String refreshToken = jwtService.generateRefreshToken(userDetails);
        String token = jwtService.generateToken(userDetails);
        return LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .isAuthenticated(true)
                .build();
    }
}
