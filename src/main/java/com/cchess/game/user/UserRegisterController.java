package com.cchess.game.user;

import com.cchess.game.security.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRegisterController {

    private final UserRegisterService userRegisterService;
    private final OtpService otpService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponse register(@Valid @RequestBody UserRegisterRequest request) {
        return userRegisterService.register(request);
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public String verifyOtp(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        Integer otp = Integer.parseInt(requestBody.get("otp"));
        boolean isOtpValid = otpService.verifyOtp(email, otp);
        if (!isOtpValid) return "Invalid OTP";

        userRegisterService.verifyUser(email);
        return "User verified";
    }

}
