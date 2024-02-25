package com.jwt.demo.Springjwtauthentication.user.api;

import com.jwt.demo.Springjwtauthentication.jwt.JwtTokenUtility;
import com.jwt.demo.Springjwtauthentication.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    JwtTokenUtility utility;

    @Autowired
    AuthenticationManager authManager;

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid AuthRequest request){
        try{
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user  = (User) authentication.getPrincipal();
            final String accessToken = utility.generateAccessToken(user);
            AuthResponse response = new AuthResponse(accessToken, user.getEmail());

            return ResponseEntity.ok(response);

        }catch(BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

    }
}
