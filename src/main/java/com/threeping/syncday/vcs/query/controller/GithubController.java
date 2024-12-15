package com.threeping.syncday.vcs.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.vcs.query.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/github/install")
public class GithubController {

    private final GithubService githubService;

    @GetMapping("/users/{userId}")
    public ResponseDTO<?> findByUserId(@PathVariable("userId") Long userId) {
        return ResponseDTO.ok(githubService.getByUserId(userId));
    }

    @GetMapping("/users/{userId}/installs/{installationId}/access_token")
    public ResponseDTO<?> getAccessToken(@PathVariable("userId") Long userId, @PathVariable("installationId") Long installationId) throws IOException {
        return ResponseDTO.ok(githubService.getAccessToken(userId, installationId));
    }
}
