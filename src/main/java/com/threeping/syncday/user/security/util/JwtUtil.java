package com.threeping.syncday.user.security.util;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;

import com.threeping.syncday.user.command.aggregate.vo.CustomUser;
import com.threeping.syncday.user.command.application.service.AppUserService;
import com.threeping.syncday.user.command.application.service.AppUserServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

// Token의 유효성 검사
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtUtil {

    private final Key secret;
    private final long accessExpiration;
    private final long refreshExpiration;
    private final AppUserService appUserService;

    @Autowired
    public JwtUtil(@Value("${token.secret}") String secret,
                   @Value("${token.access-expiration-time}") long accessExpiration,
                   @Value("${token.refresh-expiration-time}") long refreshExpiration, AppUserServiceImpl appUserService) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secret = Keys.hmacShaKeyFor(keyBytes);
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.appUserService = appUserService;
    }

    // accessToken으로부터 Claims추출
    public Claims parseClaims(String accessToken) {
        log.info("Claim 추출하기 위한 accessToken: {}", accessToken);
        Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(accessToken).getBody();
        return claims;
    }

    // refreshToken으로부터 Email 추출
    public String getEmailFromToken(String refreshToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(refreshToken).getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new CommonException(ErrorCode.EXPIRED_TOKEN_ERROR);
        }
    }

    // At 생성 로직
    public String generateAccessToken(String userEmail) {

        CustomUser user = (CustomUser) appUserService.loadUserByUsername(userEmail);

        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("userName", user.getUsername());
        claims.put("userId", user.getUserId());
        claims.put("profilePhoto", user.getProfileUrl());

        claims.put("auth", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return buildToken(claims, accessExpiration);
    }

    // Token 생성 공통 로직
    private String buildToken(Claims claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512 ,secret)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        Claims claims = parseClaims(token);

        return claims.getExpiration().before(new Date());
    }

    public Long getRemainingTime(String token) {
        Claims claims = parseClaims(token);

        return claims.getExpiration().getTime() - new Date().getTime();
    }


}