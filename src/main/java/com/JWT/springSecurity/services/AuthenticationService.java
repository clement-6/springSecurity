package com.JWT.springSecurity.services;

import com.JWT.springSecurity.dto.JwtAuthenticationResponse;
import com.JWT.springSecurity.dto.RefreshTokenRequest;
import com.JWT.springSecurity.dto.SignUpRequest;
import com.JWT.springSecurity.dto.SigninRequest;
import com.JWT.springSecurity.model.Users;

public interface AuthenticationService {

    Users signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
