package com.threeping.syncday.workspace.query.aggregate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WorkspaceDTO {

    @JsonProperty("workspace_id")
    private Long workspaceId;

    @JsonProperty("workspace_name")
    private String workspaceName;

    @JsonProperty("vcs_repo_url")
    private String vcsRepoUrl;

    @JsonProperty("proj_id")
    private Long projId;
}
