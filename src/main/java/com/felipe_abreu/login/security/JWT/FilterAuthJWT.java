package com.felipe_abreu.login.security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipe_abreu.login.model.UserModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.felipe_abreu.login.config.Constants.TOKEN_EXPIRATION;
import static com.felipe_abreu.login.config.Constants.TOKEN_PASSWORD;

public class FilterAuthJWT extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public FilterAuthJWT(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserModel userModel = (UserModel) authResult.getPrincipal();
        String token = JWT.create().withSubject(userModel.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));
        response.getWriter().write(token);
        response.getWriter().flush();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserModel userModel = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getLogin(), userModel.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Erro na autenticação!", e);
        }
    }

}
