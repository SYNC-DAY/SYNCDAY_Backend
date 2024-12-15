package com.threeping.syncday.vcs.query.aggregate.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVcsInstallationDTO {
    Long id;

    Long userId;

    Long installationId;

    String scope;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    LocalDateTime deletedAt;

    VcsInstallationDTO vcsInstallation;
}