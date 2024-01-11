package com.codesver.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);   // Server 응답 시 JSON 을 JS 에서 처리할 수 있도록 설정
        config.addAllowedOrigin("*");   // 모든 IP 에 응답을 허용
        config.addAllowedHeader("*");   // 모든 Header 에 응답을 허용
        config.addAllowedMethod("*");   // 모든 요청을 허용
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
