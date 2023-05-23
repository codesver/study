package com.codesver.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.codesver.jwt.config.auth.PrincipleDetails;
import com.codesver.jwt.model.User;
import com.codesver.jwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Security 가진 filter 중에 BasicAuthenticationFilter 사용
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 통과
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    // 인증이나 권한 요청이 있을 때 doFilterInternal 호출
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Authentication or Authorization is requested");
        String jwtHeader = request.getHeader("Authorization");
        log.info("JWT Header = {}", jwtHeader);

        // JWT header 가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            log.info("Does not have JWT");
            chain.doFilter(request, response);
            return;
        }

        // JWT 검증
        String jwtToken = jwtHeader.replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC512("cors"))
                .build().verify(jwtToken).getClaim("username").asString();

        // 서명이 확인 완료
        if (username != null) {
            log.info("Client request is authenticated");
            User user = userRepository.findByUsername(username);
            log.info("User = {}", user);
            PrincipleDetails principleDetails = new PrincipleDetails(user);

            // 서명이 정상이면 Authentication 객체 생성
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principleDetails, null, principleDetails.getAuthorities());

            // 강제로 security session 에 접근하여 authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
