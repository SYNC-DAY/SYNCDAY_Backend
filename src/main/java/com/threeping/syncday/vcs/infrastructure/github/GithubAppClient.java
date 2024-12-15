package com.threeping.syncday.vcs.infrastructure.github;


import com.threeping.syncday.vcs.command.utils.GithubFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHAppInstallation;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class GithubAppClient {
    private final GithubFactory githubFactory;

    public GHAppInstallation getGithubInstallation(Long installationId) {
        try {
            GHAppInstallation inst = githubFactory.createGithub().getApp().getInstallationById(installationId);
            log.info("inst: {}", inst);
            return inst;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
