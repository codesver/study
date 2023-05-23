package com.codesver.jwt.config;

import com.codesver.jwt.config.jwt.JwtAuthenticationFilter;
import com.codesver.jwt.config.jwt.JwtAuthorizationFilter;
import com.codesver.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http
                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
    }
}
