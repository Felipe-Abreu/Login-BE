package com.felipe_abreu.login.security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.felipe_abreu.login.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.felipe_abreu.login.config.Constants.*;

@Component
public class ValidFilterJWT extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public ValidFilterJWT(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorization = request.getHeader(HEADER_ATTRIBUTE);

        if (authorization == null || !authorization.startsWith(PREFIX_ATTRIBUTE)) {
            chain.doFilter(request, response);
            return;
        }
        String token = authorization.replace(PREFIX_ATTRIBUTE, "");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {

        var subject = JWT.require(Algorithm.HMAC512(TOKEN_PASSWORD)).build().verify(token).getSubject();

        var user = userRepository.findByLogin(subject);

        if (subject == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

    }
}
