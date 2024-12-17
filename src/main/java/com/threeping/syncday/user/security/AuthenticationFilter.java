package com.threeping.syncday.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.user.command.aggregate.vo.CustomUser;
import com.threeping.syncday.user.command.aggregate.vo.LoginRequestVO;

import com.threeping.syncday.user.command.aggregate.vo.TokenPair;
import com.threeping.syncday.user.command.application.service.AppUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestVO creds = objectMapper.readValue(request.getInputStream(), LoginRequestVO.class);
            log.info("Attempting authentication for user: {}", creds.email());

            CustomUser user = (CustomUser) appUserService.loadUserByUsername(creds.email());

            if(!bCryptPasswordEncoder.matches(creds.password(), user.getPassword())) {
                throw new BadCredentialsException("Wrong password");
            }
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, creds.password(), new ArrayList<>());

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to process authentication request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        CustomUser user = (CustomUser) authResult.getPrincipal();

        // JwtTokenProvider를 사용하여 토큰 쌍 생성
        TokenPair tokens = jwtTokenProvider.createTokenPair(user, authResult.getAuthorities());

        // Redis에 Refresh Token 저장
        storeRefreshToken(user.getUserEmail(), tokens.refreshToken());

        // Response에 토큰 추가
        addTokensToResponse(response, tokens);

        // 마지막 접속 시간 업데이트
        updateLastAccessTime(user.getUsername());

        // 성공 응답 전송
        sendSuccessResponse(response);
    }

    private void storeRefreshToken(String username, String refreshToken) {
        String redisKey = "RT:" + username;
        redisTemplate.opsForValue().set(
                redisKey,
                refreshToken,
                7, // refresh token 만료 기간 (일)
                TimeUnit.DAYS
        );
    }

    private void addTokensToResponse(HttpServletResponse response, TokenPair tokens) {
        // Access Token을 Authorization 헤더에 추가
        response.addHeader("Authorization", "Bearer " + tokens.accessToken());

        // Refresh Token을 HttpOnly 쿠키로 설정
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokens.refreshToken())
                .httpOnly(true)
                .secure(false) // http (개발환경)
//                .secure(true)  // HTTPS에서만 전송
                .sameSite("Strict")
                .path("/api") // cookie 사용 경로 지정
                .maxAge(Duration.ofDays(7))  // refresh token과 동일한 만료 기간
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    private void updateLastAccessTime(String username) {
        try {
            appUserService.updateLastActivatedAt(username);
        } catch (Exception e) {
            log.error("Failed to update last access time for user: {}", username, e);
        }
    }

    private void sendSuccessResponse(HttpServletResponse response) throws IOException {
        ResponseDTO<String> responseDTO = ResponseDTO.ok("로그인에 성공했습니다.");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(response.getWriter(), responseDTO);
    }

//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request,
//                                              HttpServletResponse response,
//                                              AuthenticationException failed) throws IOException {
//        log.error("Authentication failed: {}", failed.getMessage());
//
//        ResponseDTO<String> responseDTO = ResponseDTO.error(failed.getMessage());
//
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        objectMapper.writeValue(response.getWriter(), responseDTO);
//    }
}