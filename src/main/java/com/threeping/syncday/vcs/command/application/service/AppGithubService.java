package com.threeping.syncday.vcs.command.application.service;

import com.threeping.syncday.vcs.command.aggregate.dto.VcsInstallationDTO;
import com.threeping.syncday.vcs.command.aggregate.request.GithubInstallationRequest;

import java.io.IOException;

public interface AppGithubService {
    VcsInstallationDTO processInstallationAuth(GithubInstallationRequest req);

    Boolean deleteInstallation(Long userId, Long installationId) throws IOException;
}
