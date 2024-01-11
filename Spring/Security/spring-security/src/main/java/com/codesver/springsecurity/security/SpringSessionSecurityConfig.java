package com.codesver.springsecurity.security;

import com.codesver.springsecurity.security.oauth.PrincipleOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSessionSecurityConfig {

    private final PrincipleOauth2UserService principleOauth2UserService;
    private final AuthFailureHandler authFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Authentication
                        .requestMatchers("/member/**").authenticated()
                        // Authorization
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // No Authentication or Authorization
                        .anyRequest().permitAll()
                ).oauth2Login(login -> login
                        .loginPage("/login-form")
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig
                                        .userService(principleOauth2UserService))
                        .failureHandler(authFailureHandler)
                ).formLogin(login -> login
                        .loginPage("/login-form")       // Request URL for login form
                        .loginProcessingUrl("/login")   // Request URL for login
                        .defaultSuccessUrl("/")         // Redirection after login
                        .failureHandler(authFailureHandler)
                ).build();
    }
}
