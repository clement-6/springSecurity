package com.JWT.springSecurity.services.impl;

import com.JWT.springSecurity.dto.JwtAuthenticationResponse;
import com.JWT.springSecurity.dto.RefreshTokenRequest;
import com.JWT.springSecurity.dto.SignUpRequest;
import com.JWT.springSecurity.dto.SigninRequest;
import com.JWT.springSecurity.model.Role;
import com.JWT.springSecurity.model.Users;
import com.JWT.springSecurity.repository.UserRepo;
import com.JWT.springSecurity.services.AuthenticationService;
import com.JWT.springSecurity.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public void run(){
        Users adminAccount = userRepo.findByRole(Role.ADMIN);
        if (null == adminAccount){
            Users users = new Users();

            users.setEmail("admin@gmail.com");
            users.setFirstname("admin");
            users.setLastname("admin");
            users.setRole(Role.ADMIN);
            users.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepo.save(users);
        }
    }

    public Users signUp(SignUpRequest signUpRequest){

        Users users = new Users();
        users.setFirstname(signUpRequest.getFirstName());
        users.setLastname(signUpRequest.getLastName());
        users.setEmail(signUpRequest.getEmail());
        users.setRole(Role.USER);
        users.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return  userRepo.save(users);

    }

    public JwtAuthenticationResponse signIn(SigninRequest signinRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),
                signinRequest.getPassword()));

        var user = userRepo.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password!"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        Users user = userRepo.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }

}
