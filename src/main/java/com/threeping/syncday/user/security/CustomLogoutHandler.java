package com.threeping.syncday.user.security;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.user.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomLogoutHandler.class);
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        log.info("로그아웃 handler method 작동 시작");
        String accessToken = extractAccessToken(request);
        if(accessToken != null) {
            try {
                Claims claims = jwtUtil.parseClaims(accessToken);
                String email = claims.getSubject();
                // redis에서 rt제거
                redisTemplate.delete("RT:" + email);
                log.info("redis에서 RT 제거됌");

                // redis에 at 블랙리스트로 등록(서버 차원에서 at를 만료시킬 방법이 없으므로)
                // at 토큰의 남은 저장 시간을 redis에 저장해놓고 시간이 지나면 자동 삭제되도록 구현
                Long expiration = claims.getExpiration().getTime() - new Date().getTime();
                log.info("accessToken 남은 시간 :{} " + expiration);
                redisTemplate.opsForValue().set(
                        "BL:" + accessToken,
                        "logout",
                        expiration,
                        TimeUnit.MILLISECONDS
                );
            } catch (Exception e) {
                log.info("로그아웃 시 refreshToken을 redis에서 삭제하려고 할 때 오류가 발생한 경우의 로그");
                throw new CommonException(ErrorCode.EXPIRED_TOKEN_ERROR);
            }
        }
        // cookie 만료
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false) // 개발 환경에선 false
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();
        log.info("로그아웃 처리된 Cookie: {}", responseCookie);
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    private String extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}