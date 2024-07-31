package com.demo.studycode.config;

import com.demo.studycode.security.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
                                .requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자 관련 모든 요청에 대해 ADMIN 권한만 허용
                                .requestMatchers("/api/user/**").authenticated() // 일반유저 관련 모든 요청에 대해 승인된 사용자만 허용
                                .requestMatchers("/**").permitAll() // 모든 경로 대해 모든 사용자 허용
                )
                .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class); // JwtTokenFilter로 토큰 유효성 검사
        return http.build();
    }


    @Bean
    public PasswordEncoder PasswordEncoder() { // BCryptPasswordEncoder의 interface. 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Spring 보안 및 인증 담당
        return authenticationConfiguration.getAuthenticationManager();
    }

    public void configure(WebSecurity web) throws Exception {
        web.httpFirewall(defaultHttpFirewall());
    }

    @Bean
    HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
}
