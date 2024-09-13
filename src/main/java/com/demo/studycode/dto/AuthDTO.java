package com.demo.studycode.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthDTO { // 인증요청을 보내거나 토큰정보 응답 시 사용되는 데이터 객체

    private Long idx;
    private String userid;
    private String name;
    private String accessToken;
    private int expiresAt;
    private String grantType;

}