package com.cchess.game.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {

    @Size(min = 2, message = "USERNAME_INVALID")
    private String username;

    @Email(message = "EMAIL_INVALID")
    private String email;

    @Size(min = 6, message = "PASSWORD_INVALID")
    private String password;

}
