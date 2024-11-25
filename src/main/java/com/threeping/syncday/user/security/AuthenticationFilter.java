package com.threeping.syncday.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.user.command.application.service.UserCommandService;
import com.threeping.syncday.user.command.domain.aggregate.CustomUser;
import com.threeping.syncday.user.command.domain.vo.LoginRequestVO;
import com.threeping.syncday.user.query.service.UserQueryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final Environment env;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserQueryService userQueryService,
                                UserCommandService userCommandService,
                                Environment env,
                                RedisTemplate<String, String> redisTemplate) {
        super(authenticationManager);
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
        this.env = env;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 기반 인증 시작");
        log.info("attemptAuthentication method request LocalPort: {}", request.getLocalPort());
        try {
            LoginRequestVO creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestVO.class);
            log.info("attemptAuthentication method creds객체 정보 : {}", creds);

            CustomUser user = (CustomUser) userQueryService.loadUserByUsername(creds.getEmail());

            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, creds.getPassword(), new ArrayList<>());
            log.info("attemptAuthentication authToken(성공 후 생성된 인증 토큰): {}", authToken);

            // AuthenticationManager에 전달
            Authentication auth = getAuthenticationManager().authenticate(authToken);
            log.info("Authentication result: {}", auth);  // 이 로그가 찍히는지 확인

            return auth;
        } catch (IOException e) {
            throw new AuthenticationServiceException("유저 인증 실패", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        log.info("successfulAuthentication! Principal 객체 : {}", authResult);

        // 고유 UserType으로 다운캐스팅
        CustomUser customUser = (CustomUser) authResult.getPrincipal();

        // accessToken에는 회원 아이디, 회원 번호, 이름, 프로필 사진까지 넣기
        String email = customUser.getUsername();
        log.info("email: {}", email);

        Claims acessClaims = Jwts.claims().setSubject(email); // 회원 정보
        acessClaims.put("userName", customUser.getUserName());
        acessClaims.put("userId", customUser.getUserId());
        acessClaims.put("profilePhoto", customUser.getProfilePhoto());

        // refreshToken에는 아이디만 넣기
        Claims refreshClaims = Jwts.claims().setSubject(email);
        List<String> roles = authResult.getAuthorities().stream()
                .map(role -> role.getAuthority())
                .collect(Collectors.toList()); // 권한 정보
        acessClaims.put("auth", roles);

        // 토큰 만료 시간 설정
        long accessExpiration =
                System.currentTimeMillis() + getExpirationTime(env.getProperty("token.access-expiration-time"));
        long refreshExpiration =
                System.currentTimeMillis() + getExpirationTime(env.getProperty("token.refresh-expiration-time"));

        // AT 생성
        String accessToken = Jwts.builder()
                .setClaims(acessClaims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        // RT 생성
        String refreshToken = Jwts.builder()
                .setClaims(refreshClaims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        // Ex ) Key: RT:abc@gmail.com, Value: RT정보, TTL(Data 만료시간)
        redisTemplate.opsForValue().set(
                "RT:" + email,
                refreshToken,
                refreshExpiration,
                TimeUnit.MILLISECONDS);

        // RT는 HttpOnly Cookie에 담기
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true) // js를 통한 쿠키 완전히 차단, document.cookie로 쿠키 읽기/쓰기 불가, 오직 https 통신으로만 쿠키 전송 가능
                        .secure(false) // 개발 환경에선 http 통신이므로
                        .sameSite("Strict") // csrf 공격 방지
                        .path("/api")   // cookie 사용 가능 경로 지정
                        .build();

        // at 헤더에 담기
        response.addHeader("Authorization", "Bearer " + accessToken);
        // Cookie를 헤더에 담아 전송
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        // user 상세 정보 조회

        // 마지막 로그인 시간 업데이트
        userCommandService.updateLastAccessTime(email);

        ResponseDTO<String> responseDTO = ResponseDTO.ok("로그인에 성공했습니다.");

        //JSON 문자열로 변환
        String JSON = new ObjectMapper().writeValueAsString(responseDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON);
    }

    private long getExpirationTime(String expirationTime) {
        if (expirationTime == null) {
            // 주어진 값이 없을 때 기본 시간 설정
            return 3600000;
        }
        return Long.parseLong(expirationTime);
    }
}
