package com.threeping.syncday.vcs.query.service;

import com.threeping.syncday.vcs.command.aggregate.entity.UserVcsInstallation;
import com.threeping.syncday.vcs.infrastructure.github.GithubAppClient;
import com.threeping.syncday.vcs.query.aggregate.dto.UserVcsInstallationDTO;
import com.threeping.syncday.vcs.query.aggregate.dto.VcsInstallationDTO;
import com.threeping.syncday.vcs.query.repository.UserVcsInstallationMapper;
import com.threeping.syncday.vcs.query.repository.VcsInstallationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {
private final VcsInstallationMapper vcsInstallationMapper;
private final UserVcsInstallationMapper userVcsInstallationMapper;
private final GithubAppClient githubAppClient;
    @Override
    public List<VcsInstallationDTO> getByUserId(Long userId) {
        return vcsInstallationMapper.selectByUserIdAndVcsType(userId, "GITHUB");
    }

    @Override
    public String getAccessToken(Long userId, Long installationId) throws IOException {
        UserVcsInstallationDTO userVcsInstallation = userVcsInstallationMapper.selectByUserIdAndInstallationId(userId, installationId);
        if (userVcsInstallation == null) {
            return null;
        }

        return githubAppClient.getGithubInstallation(userVcsInstallation.getVcsInstallation().getInstallationId()).createToken().create().getToken();
    }
}
