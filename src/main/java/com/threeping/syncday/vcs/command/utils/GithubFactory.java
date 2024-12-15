package com.threeping.syncday.vcs.command.utils;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHAppInstallationToken;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class GithubFactory {

    private final GithubJwtUtils githubJwtUtils;

    private GHAppInstallationToken appInstallationToken(Long installationId) throws java.io.IOException {
        String jwt = githubJwtUtils.createJwt();

        GitHub github = new GitHubBuilder().withJwtToken(jwt).build();
        log.info("github: {}", github);
        return github.getApp().getInstallationById(installationId).createToken().create();
    }
    public GitHub createGithubWithInstallationToken(Long installationId) throws IOException, java.io.IOException{
        GHAppInstallationToken installationToken = this.appInstallationToken(installationId);
        log.info("installationToken: {}", installationToken);
        log.info("installationToken.getToken(): {}", installationToken.getToken());
        GitHub github = new GitHubBuilder().withAppInstallationToken(installationToken.getToken()).build();
        log.info("github: {}", github);
        return github;
    }
    public GitHub createGithub() throws java.io.IOException {
        String jwt = githubJwtUtils.createJwt();
        GitHub githubApp = new GitHubBuilder().withJwtToken(jwt).build();
        log.info("githubApp: {}", githubApp);
        return githubApp;
    }



}
