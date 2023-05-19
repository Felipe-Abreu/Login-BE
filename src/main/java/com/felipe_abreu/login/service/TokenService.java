package com.felipe_abreu.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.felipe_abreu.login.model.JwtToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.felipe_abreu.login.config.Constants.TOKEN_EXPIRATION;
import static com.felipe_abreu.login.config.Constants.TOKEN_PASSWORD;

@Service
public class TokenService {

    public JwtToken createToken(Authentication authentication) {

        User userLogin = (User) authentication.getPrincipal();
        Date expiresAt = new Date(System.currentTimeMillis() + TOKEN_EXPIRATION);
        long minutes = Duration.between(new Date().toInstant(), expiresAt.toInstant()).toMillis();

        var token = JWT.create()
                .withSubject(userLogin.getUsername())
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));

        return new JwtToken(token, minutes);
    }

}
