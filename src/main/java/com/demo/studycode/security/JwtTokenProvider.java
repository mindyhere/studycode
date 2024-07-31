package com.demo.studycode.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger LOGGER  = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String SECURITY_KEY = "My-Security-Key";

    // JWT 생성 메서드
    public String generateToken(String email, int duration) {
        try {
            // 현재 시간 기준 1시간 뒤로 만료시간 설정
            Instant now = Instant.now();
            Instant expireTime = now.plusSeconds(duration);

            // JWT Claim 설정
            // Claim 집합 -> 내용 설정 (페이로드 설정)
            // subject -> "sub", issuer << "iss", expiration time << "exp"
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(expireTime))
                    .build();

            // JWT 서명
            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),    // 헤더 설정
                    claimsSet
            );

            // HMAC 서명을 사용하여 JWT 서명
            JWSSigner signer = new MACSigner(SECURITY_KEY.getBytes());    // 서명 설정
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            return null;
        }
    }

    // JWT 검증
    public String validateToken(String token) {
        try {
            // 서명 확인을 통한 JWT 검증
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECURITY_KEY.getBytes());
            if (!signedJWT.verify(verifier)) { // 서명이 유효하지 않은 경우
                return null;
            }

            Date expiresAt = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (!this.validExpireTime(expiresAt)) { // 만료시간이 지난 경우
                return null;
            }

            String email = signedJWT.getJWTClaimsSet().getSubject();
            return email;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    // 토큰 만료시간 검증
    private boolean validExpireTime(Date expiresAt){
        // LocalDateTime으로 만료시간 변경
        LocalDateTime localTimeExpired = expiresAt.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        // 현재 시간이 만료시간의 이전 -> true 반환
        return LocalDateTime.now().isBefore(localTimeExpired);
    }

}