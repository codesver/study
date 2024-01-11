package com.codesver.jwt.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Filter 3");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // 정상적인 ID, PW 으로 로그인되면 token 을 생성하고 응답
        // Client 는 요청마다 header 의 Authorization 에 token 포함
        // Token 이 넘어왔을 때 token 이 server 에서 생성한 token 인지 확인 (RSA, HS256)
        if (req.getMethod().equals("POST")) {
            String headerAuth = req.getHeader("Authorization");
            log.info("Header authorization = {}", headerAuth);

            if (headerAuth.equals("cors")) {    // Token : cors
                filterChain.doFilter(req, res);
            } else {
                PrintWriter writer = res.getWriter();
                writer.println("Not authenticated");
            }
        }
    }
}
