package org.binaracademy.finalproject.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    private String googleId;
    @NotNull(message = "user id shouldn't be null")
    private Long userId;

    @Email(message = "email address is invalid")
    private String email;

    @Email(message = "password address is invalid")
    private String password;
}
