package com.JWT.springSecurity.controller;

import com.JWT.springSecurity.dto.JwtAuthenticationResponse;
import com.JWT.springSecurity.dto.RefreshTokenRequest;
import com.JWT.springSecurity.dto.SignUpRequest;
import com.JWT.springSecurity.dto.SigninRequest;
import com.JWT.springSecurity.model.Users;
import com.JWT.springSecurity.services.AuthenticationService;
import com.JWT.springSecurity.services.impl.AuthenticationServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @PostConstruct
    public void run(){authenticationServiceImpl.run();}

    @PostMapping("/signUp")
    public ResponseEntity<Users> signUp(@RequestBody SignUpRequest signUpRequest){
        return new ResponseEntity<>(authenticationService.signUp(signUpRequest), HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SigninRequest signinRequest){
        return new ResponseEntity<>(authenticationService.signIn(signinRequest), HttpStatus.OK);
    }

     @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return new ResponseEntity<>(authenticationService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }


}
