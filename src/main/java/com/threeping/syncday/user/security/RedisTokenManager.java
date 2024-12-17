package com.threeping.syncday.user.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisTokenManager {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String PREFIX = "RT:";

    public void saveRefreshToken(String username, String refreshToken, long expirationTime) {
        String key = formatKey(username);
        redisTemplate.opsForValue()
                .set(key, refreshToken, expirationTime, TimeUnit.MILLISECONDS);
        log.info("Refresh token saved for user: {}", username);
    }

    public String getRefreshToken(String username) {
        String key = formatKey(username);
        String token = redisTemplate.opsForValue().get(key);
        if (token == null) {
            log.info("No refresh token found for user: {}", username);
        }
        return token;
    }

    public void deleteRefreshToken(String username) {
        String key = formatKey(username);
        Boolean deleted = redisTemplate.delete(key);
        if (Boolean.TRUE.equals(deleted)) {
            log.info("Refresh token deleted for user: {}", username);
        }
    }

    public boolean hasRefreshToken(String username) {
        String key = formatKey(username);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    private String formatKey(String username) {
        return PREFIX + username;
    }
}