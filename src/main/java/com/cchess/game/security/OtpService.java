package com.cchess.game.security;

import com.cchess.game.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    public Integer generateOtp(String email) {
        if (isRequested(email)) {
            throw new BadRequestException("Otp already requested");
        }

        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        String encodedOtp = passwordEncoder.encode(String.valueOf(otp));

        redisTemplate.opsForValue().set(email, encodedOtp, 2, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(email + ":requested", "true", 1, TimeUnit.MINUTES);

        return otp;
    }

    private boolean isRequested(String email) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(email + ":requested"));
    }

    public boolean verifyOtp(String email, Integer otp) {
        String savedOtp = redisTemplate.opsForValue().get(email);

        if (savedOtp != null && passwordEncoder.matches(String.valueOf(otp), savedOtp)) {
            redisTemplate.delete(email);
            return true;
        }
        return false;
    }

}
