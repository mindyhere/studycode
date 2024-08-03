package com.demo.studycode.config;

import com.demo.studycode.security.CustomUserDetailsService;
import com.demo.studycode.security.JwtFilter;
import com.demo.studycode.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
// Annotation을 통해 Controller의 API 보안 수준 설정하도록 활성화
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF, CORS
        http.csrf((csrf) -> csrf.disable());
        http.cors(Customizer.withDefaults());

        // 세션 관리 상태 없음으로 구성(Spring Security가 세션 생성 or 사용 X)
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // FormLogin, BasicHttp 비활성화
        // Spring 웹 페이지에서 제공되는 로그인 폼을 통해 사용자를 인증하는 메커니즘과 HTTP 기반 기본 인증을 비활성화
        http.formLogin((form) -> form.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        // JwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(new JwtFilter(customUserDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 권한 규칙
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
//                            .requestMatchers("/api/user/**").authenticated() // 일반유저 관련 모든 요청에 대해 승인된 사용자만 허용
//                            .requestMatchers("/api/auth/**").permitAll() // 모든 경로 대해 모든 사용자 허용
                                // @PreAuthrization을 사용할 것이기 때문에 모든 경로에 대한 인증처리는 Pass
                                .requestMatchers("/**").permitAll() // 모든 경로 대해 모든 사용자 허용
                                .anyRequest().permitAll()
                );

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
