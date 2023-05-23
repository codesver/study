package com.codesver.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.codesver.jwt.config.auth.PrincipleDetails;
import com.codesver.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Date;

// Spring security UsernamePasswordAuthenticationFilter 가 있음
// /login request 에서 username, password 를 전송(POST) 하면 동작
// formLogin 을 disable 했기 때문에 미동작
// Security Config 에 등록
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter : Trying to login");

        // request 에 있는 username 과 password 를 파싱해서 자바 Object 로 받기
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(request.getInputStream(), User.class);
        log.info("User = {}", user);

        // Token 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        log.info("Token 생성 완료 = {}", authenticationToken);

        // 정상적인 로그인 시도 - authenticationManager 로 로그인하면 PrincipleDetailsService 호출
        // PrincipalDetailsService 의 loadUserByUsername() 함수 호출
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        PrincipleDetails principleDetails = (PrincipleDetails) authentication.getPrincipal();
        log.info("Authentication = {}", principleDetails.getUser().getUsername());

        // Authentication 객체가 session 에 저장 => 권한 처리를 security 가 처리
        return authentication;
    }

    // attemptAuthentication 으로 인증이 정상적으로 되었으면 실행
    // JWT token 을 생성하여 client 에게 response
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Authentication is completed");
        PrincipleDetails principleDetails = (PrincipleDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principleDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60000 * 10))
                .withClaim("id", principleDetails.getUser().getUsername())
                .withClaim("username", principleDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("cors"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
