package com.threeping.syncday.user.security;

import com.threeping.syncday.user.command.aggregate.vo.CustomUser;
import com.threeping.syncday.user.command.aggregate.vo.TokenPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
public class JwtTokenProvider {

    private final Key secret;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;

    public JwtTokenProvider(
            @Value("${token.secret}") String secret,
            @Value("${token.access-expiration-time}") Long accessTokenExpiration,
            @Value("${token.refresh-expiration-time}") Long refreshTokenExpiration) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secret = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public TokenPair createTokenPair(CustomUser user, Collection<? extends GrantedAuthority> authorities) {
        String accessToken = createAccessToken(user, authorities);
        String refreshToken = createRefreshToken(user.getUsername());
        return new TokenPair(accessToken, refreshToken);
    }

    private String createAccessToken(CustomUser user, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("userId", user.getUserId());
        claims.put("userName", user.getUsername());
        claims.put("profileUrl", user.getProfileUrl());
        claims.put("auth", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return buildToken(claims, accessTokenExpiration);
    }

    private String createRefreshToken(String username) {
        Claims claims = Jwts.claims().setSubject(UUID.randomUUID().toString());
        claims.put("username", username);

        return buildToken(claims, refreshTokenExpiration);
    }

    private String buildToken(Claims claims, Long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secret, SignatureAlgorithm.HS512)
                .compact();
    }


}