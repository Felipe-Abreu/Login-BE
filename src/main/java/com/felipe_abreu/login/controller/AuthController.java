package com.felipe_abreu.login.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe_abreu.login.model.JwtToken;
import com.felipe_abreu.login.model.UserModel;
import com.felipe_abreu.login.service.TokenService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.felipe_abreu.login.config.Constants.HEADER_ATTRIBUTE;

@RestController
@RequestMapping("api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody UserModel login) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        JwtToken jwtToken = tokenService.createToken(authenticate);
        final var jwt = jwtToken.getToken();
        final var expiredToken = jwtToken.getExpiredToken();
        final var httHeaders = new HttpHeaders();
        httHeaders.add(HEADER_ATTRIBUTE, "Bearer " + jwt);
        return new ResponseEntity<>(new JwtToken(jwt, expiredToken), httHeaders, HttpStatus.OK);

    }
}
