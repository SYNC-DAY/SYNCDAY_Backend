package com.threeping.syncday.workspace.query.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WorkspaceInfoDTO {

    @JsonProperty("workspace_id")
    private Long workspaceId;

    @JsonProperty("workspace_name")
    private String workspaceName;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("progress_status")
    private Byte progressStatus;

    @JsonProperty("proj_id")
    private Long projId;

    @JsonProperty("vcs_type")
    private String vcsType;

    @JsonProperty("vcs_repo_name")
    private String vcsRepoName;

    @JsonProperty("vcs_repo_url")
    private String vcsRepoUrl;
    @JsonProperty("cardboards")
    private List<CardboardVO> cardboards;
}
