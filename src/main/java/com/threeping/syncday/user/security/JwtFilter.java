package com.threeping.syncday.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.user.command.aggregate.vo.CustomUser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final RedisTokenManager redisTokenManager;
    private final JwtUtil jwtUtil;
    private final TokenRefreshService tokenRefreshService;
    private final ObjectMapper objectMapper;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = resolveToken(request);
            if (!StringUtils.hasText(accessToken)) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                // First try to validate and use the access token
                if (jwtUtil.validateToken(accessToken)) {
                    setAuthenticationToContext(accessToken, request);
                    filterChain.doFilter(request, response);
                    return;
                }
            } catch (ExpiredJwtException expiredAccessEx) {
                // Access token is expired, proceed to refresh token logic
                log.debug("Access token expired, attempting refresh flow");
                handleTokenRefresh(request, response, filterChain);
                return;
            } catch (JwtException e) {
                log.error("Invalid access token", e);
                handleAuthenticationFailure(response, ErrorCode.INVALID_TOKEN_ERROR);
                return;
            }

            // If we get here, token was invalid but not expired
            handleAuthenticationFailure(response, ErrorCode.INVALID_TOKEN_ERROR);

        } catch (Exception e) {
            log.error("Authentication process failed", e);
            handleAuthenticationFailure(response, ErrorCode.LOGIN_FAILURE);
        }
    }

    private void handleTokenRefresh(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String refreshToken = extractRefreshTokenFromCookie(request);
        if (refreshToken == null) {
            handleAuthenticationFailure(response, ErrorCode.NOT_FOUND_REFRESH_TOKEN);
            return;
        }

        try {
            // Validate refresh token
            if (!jwtUtil.validateToken(refreshToken)) {
                handleAuthenticationFailure(response, ErrorCode.INVALID_REFRESH_TOKEN);
                return;
            }

            // Verify refresh token in Redis
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            String storedRefreshToken = redisTokenManager.getRefreshToken(username);

            if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
                handleAuthenticationFailure(response, ErrorCode.EXPIRED_REFRESH_TOKEN);
                return;
            }

            // Get new token pair
            var tokenPair = tokenRefreshService.refreshTokens(refreshToken);

            // Set new access token in response header
            response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + tokenPair.accessToken());

            // Update security context with new token
            setAuthenticationToContext(tokenPair.accessToken(), request);

            // Continue with the filter chain
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            log.error("Refresh token has expired", e);
            handleAuthenticationFailure(response, ErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            log.error("Token refresh failed", e);
            handleAuthenticationFailure(response, ErrorCode.TOKEN_GENERATION_ERROR);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "refreshToken".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private void setAuthenticationToContext(String token, HttpServletRequest request) {
        CustomUser user = jwtUtil.getUserFromToken(token);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleAuthenticationFailure(HttpServletResponse response,ErrorCode code) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(
                objectMapper.writeValueAsString(ResponseDTO.fail(new CommonException(code)))
        );
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/") || path.startsWith("/api/public/");
    }
}