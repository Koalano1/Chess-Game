package com.cchess.game.security.auth.impl;

import com.cchess.game.dto.request.LoginRequest;
import com.cchess.game.dto.response.LoginResponse;
import com.cchess.game.exception.AuthenticationException;
import com.cchess.game.security.auth.AuthService;
import com.cchess.game.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        boolean matches = passwordEncoder.matches(request.getPassword(), userDetails.getPassword());
        if (!matches) {
            throw new AuthenticationException("Invalid username or password");
        }

        if (userDetails.isEnabled()) throw new AuthenticationException("User is not active");

        String refreshToken = jwtService.generateRefreshToken(userDetails);
        String token = jwtService.generateToken(userDetails);
        return LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .isAuthenticated(true)
                .build();
    }
}
