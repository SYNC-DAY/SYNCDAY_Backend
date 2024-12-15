package com.threeping.syncday.user.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.user.command.aggregate.vo.GithubTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Slf4j
public class OAuth2ServiceImpl implements OAuth2Service {
    @Value("${github.oauth2.client-id}")
    private String clientId;

    @Value("${github.oauth2.client-secret}")
    private String clientSecret;

    private RestTemplate restTemplate;
    private Environment env;

    @Autowired
    public OAuth2ServiceImpl(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }
    public GithubTokenResponse getGithubAccessToken(String code) {
        String githubAuthUrl = "https://github.com/login/oauth/access_token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Accept", "application/json"); // Add this to get JSON response

        HttpEntity<MultiValueMap<String, String>> githubTokenRequest = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<GithubTokenResponse> response = restTemplate.exchange(
                    githubAuthUrl,
                    HttpMethod.POST,
                    githubTokenRequest,
                    GithubTokenResponse.class
            );

            GithubTokenResponse tokenResponse = response.getBody();
            log.info("tokenResponse: {}", tokenResponse);

            if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
                throw new CommonException(ErrorCode.GITHUB_AUTH_ERROR);
            }
            // Check for error in response
            // Calculate token expiration times
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime accessTokenExpiry = now.plusSeconds(tokenResponse.getExpiresIn());
            LocalDateTime refreshTokenExpiry = now.plusSeconds(tokenResponse.getRefreshTokenExpiresIn());

            // TODO: Store tokens and expiration times in your database
            return tokenResponse;

        } catch (HttpClientErrorException e) {
            log.error("Github API error: {}", e.getResponseBodyAsString());
            throw new CommonException(ErrorCode.GITHUB_AUTH_ERROR);
        }
    }

    public GithubTokenResponse refreshAccessToken(String refreshToken) {
        String githubAuthUrl = "https://github.com/login/oauth/access_token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Accept", "application/json");

        HttpEntity<MultiValueMap<String, String>> refreshTokenRequest =
                new HttpEntity<>(params, headers);

        try {
            ResponseEntity<GithubTokenResponse> response = restTemplate.exchange(
                    githubAuthUrl,
                    HttpMethod.POST,
                    refreshTokenRequest,
                    GithubTokenResponse.class
            );

            GithubTokenResponse tokenResponse = response.getBody();
            if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
                throw new CommonException(ErrorCode.GITHUB_AUTH_ERROR);
            }

            // Update stored tokens and expiration times

            return tokenResponse;

        } catch (RestClientException e) {
            log.error("GitHub token refresh error", e);
            throw new CommonException(ErrorCode.GITHUB_AUTH_ERROR);
        }
    }

}
