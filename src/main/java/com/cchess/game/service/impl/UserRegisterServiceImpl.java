package com.cchess.game.service.impl;

import com.cchess.game.dto.MailBody;
import com.cchess.game.dto.request.UserRegisterRequest;
import com.cchess.game.dto.response.UserRegisterResponse;
import com.cchess.game.exception.EntityExistedException;
import com.cchess.game.exception.UsernameExistedException;
import com.cchess.game.mapper.UserMapper;
import com.cchess.game.model.entities.User;
import com.cchess.game.repository.UserRepository;
import com.cchess.game.service.EmailService;
import com.cchess.game.service.OtpService;
import com.cchess.game.service.UserRegisterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final OtpService otpService;

    @Override
    @Transactional
    public UserRegisterResponse register(UserRegisterRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new UsernameExistedException();
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User savedUser = userRepository.save(newUser);

        Integer otp = otpService.generateOtp(savedUser.getEmail());
        MailBody mailBody = MailBody.builder()
                .to(savedUser.getEmail())
                .text("Your OTP is " + otp)
                .build();
        emailService.sendSimpleMessage(mailBody);

        return userMapper.toResponse(newUser);
    }

    @Override
    @Transactional
    public void verifyUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setIsEnabled(true);
        }
    }
}
