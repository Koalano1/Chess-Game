package com.cchess.game.user;

import com.cchess.game.exception.OtpInvalidException;
import com.cchess.game.security.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserRegisterController implements UserRegisterResource {

    private final UserRegisterService userRegisterService;
    private final OtpService otpService;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        return userRegisterService.register(request);
    }

    @Override
    public String verifyOtp(Map<String, String> requestBody) {
        String email = requestBody.get("email");
        Integer otp = Integer.parseInt(requestBody.get("otp"));
        boolean isOptValid = otpService.verifyOtp(email, otp);
        if (!isOptValid) throw new OtpInvalidException();

        userRegisterService.verifyUser(email);
        return "User verified";
    }

}
