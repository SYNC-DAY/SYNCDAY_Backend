package com.threeping.syncday.user.command.application.service;

import com.threeping.syncday.user.command.aggregate.vo.GithubTokenResponse;

public interface OAuth2Service {
    GithubTokenResponse getGithubAccessToken(String code);
    GithubTokenResponse refreshAccessToken(String refreshToken);


}
