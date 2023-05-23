package com.codesver.jwt.config.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Spring security UsernamePasswordAuthenticationFilter 가 있음
// /login request 에서 username, password 를 전송(POST) 하면 동작
// formLogin 을 disable 했기 때문에 미동작
// Security Config 에 등록
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter : Trying to login");

        // 1. username, password 수신
        // 2. 정상적인 로그인 시도 - authenticationManager 로 로그인하면 PrincipleDetailsService 호출
        // PrincipalDetailsService 의 loadUserByUsername() 함수 호출
        // 3. PrincipleDetails 를 session 에 저장
        // 4. JWT 를 만들어서 응답
        return super.attemptAuthentication(request, response);
    }
}
