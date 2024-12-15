package com.threeping.syncday.vcs.command.application.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.vcs.command.aggregate.request.GithubInstallationRequest;
import com.threeping.syncday.vcs.command.application.service.AppGithubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/github")
public class AppGithubController
{
    private final AppGithubService githubService;
    @PostMapping("/install")
    public ResponseDTO<?> installGithubApp(@RequestBody GithubInstallationRequest req){
        log.info("req: {}", req);
        return ResponseDTO.ok(githubService.processInstallationAuth(req));
    }

    @DeleteMapping("/install")
    public ResponseDTO<?> uninstallGithubApp(@RequestParam Long userId, @RequestParam Long installationId) throws IOException {
        log.info("userId: {}, installationId: {}", userId, installationId);
        return ResponseDTO.ok(githubService.deleteInstallation(userId, installationId));
    }

}
