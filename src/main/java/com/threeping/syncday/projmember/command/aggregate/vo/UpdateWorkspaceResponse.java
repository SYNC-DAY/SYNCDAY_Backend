package com.threeping.syncday.projmember.command.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public record UpdateWorkspaceResponse(
        @JsonProperty("proj_id") Long projId,
        @JsonProperty("workspace_id") Long workspaceId,
        @JsonProperty("workspace_name") String workspaceName,
        @JsonProperty("created_at") Timestamp createdAt,
        @JsonProperty("progress_status") Byte progressStatus,
        @JsonProperty("vcs_repo_name") String vcsRepoName,
        @JsonProperty("vcs_repo_url") String vcsRepoUrl) {
}
