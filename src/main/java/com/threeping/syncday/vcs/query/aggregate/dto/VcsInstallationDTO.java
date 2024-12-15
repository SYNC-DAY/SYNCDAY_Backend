package com.threeping.syncday.vcs.query.aggregate.dto;

import com.threeping.syncday.vcs.command.aggregate.entity.AccountType;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class VcsInstallationDTO {
    private Long id;

    private String vcsType;
    private Long installationId;
    private String accountId;

    private String accountName;

    private AccountType accountType;

    private String avatarUrl;

    private String apiUrl;

    private String htmlUrl;


    private String status;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
