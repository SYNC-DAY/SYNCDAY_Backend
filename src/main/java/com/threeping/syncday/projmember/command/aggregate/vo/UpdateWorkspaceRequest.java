package com.threeping.syncday.projmember.command.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateWorkspaceRequest(
                                     @JsonProperty("proj_member_id") Long projMemberId,
                                     @JsonProperty("workspace_id") Long workspaceId,
                                     @JsonProperty("workspace_name") String workspaceName,
                                     @JsonProperty("proj_id")Long projId,
                                     @JsonProperty("vcs_repo_name") String vcsRepoName,
                                     @JsonProperty("vcs_repo_url") String vcsRepoUrl) {
}
