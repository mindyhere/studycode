package com.demo.studycode.dto;

import com.demo.studycode.model.Auth;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO { // 인증요청을 보내거나 토큰정보 응답 시 사용되는 데이터 객체
    private String email;
    private String passwd;
    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public AuthDTO(Auth entity) {
        this.tokenType = entity.getTokenType();
        this.accessToken = entity.getAccessToken();
        this.refreshToken = entity.getRefreshToken();
    }
}
