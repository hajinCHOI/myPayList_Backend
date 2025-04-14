package com.mypaylist.backend.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final long TOKEN_VALID_TIME = 60 * 60 * 1000L; // 1시간
    private String secretKey = "my-super-secret-jwt-key-must-be-long-enough";

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 생성
    public String createToken(String email) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + TOKEN_VALID_TIME);

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    // JWT 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 요청 헤더에서 JWT 추출
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }
    // JWT에서 사용자 정보 추출
    public String getUserEmailFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
}

    // 요청 헤더에서 JWT 추출
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
