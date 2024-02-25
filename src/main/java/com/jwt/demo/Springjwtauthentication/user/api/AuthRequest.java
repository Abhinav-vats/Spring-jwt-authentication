package com.jwt.demo.Springjwtauthentication.user.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @Email
    @Length(min = 5, max = 50)
    private String email;

    @Length(min = 5, max = 50)
    private String password;
}
