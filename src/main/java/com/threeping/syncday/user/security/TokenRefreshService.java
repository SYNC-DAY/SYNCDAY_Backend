package com.threeping.syncday.user.security;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.user.command.aggregate.vo.CustomUser;
import com.threeping.syncday.user.command.aggregate.vo.TokenPair;
import com.threeping.syncday.user.command.application.service.AppUserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenRefreshService {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;
    private final RedisTokenManager redisTokenManager;
    private final AppUserService appUserService;

    public TokenPair refreshTokens(String refreshToken) {
        // First validate the token structure and signature
        if (!jwtUtil.validateToken(refreshToken)) {
            log.error("Refresh token validation failed");
            throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);}

        try {
            // Extract claims from refresh token
            Claims claims = jwtUtil.parseClaims(refreshToken);
            String username = claims.get("username", String.class);

            if (username == null) {
                log.error("Username not found in refresh token");
                throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);}

            // Verify if refresh token exists in Redis
            String storedRefreshToken = redisTokenManager.getRefreshToken(username);
            if (storedRefreshToken == null) {
                log.error("No refresh token found in Redis for user: {}", username);
                throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);}

            if (!storedRefreshToken.equals(refreshToken)) {
                log.error("Stored refresh token doesn't match provided token for user: {}", username);
                throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR); }

            // Load fresh user details from database
            CustomUser user = (CustomUser) appUserService.loadUserByUsername(username);
            if (user == null) {
                log.error("User not found: {}", username);
                throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);  }

            // Generate new token pair
            TokenPair newTokens = jwtTokenProvider.createTokenPair(user, user.getAuthorities());

            // Update refresh token in Redis with the new one
            long refreshTokenExpiration = jwtUtil.getExpirationTime(newTokens.refreshToken()) - System.currentTimeMillis();
            redisTokenManager.saveRefreshToken(
                    username,
                    newTokens.refreshToken(),
                    refreshTokenExpiration
            );

            log.info("Successfully refreshed tokens for user: {}", username);
            return newTokens;

        } catch (Exception e) {
            log.error("Error during token refresh", e);
            throw new CommonException(ErrorCode.LOGIN_FAILURE);
        }
    }
}

