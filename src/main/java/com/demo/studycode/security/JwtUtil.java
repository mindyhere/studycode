package com.demo.studycode.security;

import com.demo.studycode.dto.AuthDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final long expireTime;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expirationTime}") long expirationTime
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expireTime = expirationTime;
    }

    // Access Token 생성
    public String createAccessToken(AuthDTO dto) {
        return createToken(dto, expireTime);
    }

    //JWT 생성
    private String createToken(AuthDTO authUser, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("id", authUser.getId());
        claims.put("email", authUser.getEmail());
        claims.put("role", authUser.getRole());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expiresAt = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expiresAt.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    // Token에서 User ID 추출
    public Long getUserId(String token) {
        return parseClaims(token).get("id", Long.class);
    }

    // Token에서 User eamil 추출
    public String getUserEmail(String token) {
        return parseClaims(token).get("email", String.class);
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    // JWT Claims 추출
    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
