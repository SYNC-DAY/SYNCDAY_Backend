package com.threeping.syncday.workspace.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class WorkspaceQueryDTO {
    private Long workspaceId;
    private String workspaceName;
    private Long projectId;
    private String projectName;
    private String vcsType;
    private String vcsRepoUrl;
    private String vcsRepoName;
    private Timestamp createdAt;
}
