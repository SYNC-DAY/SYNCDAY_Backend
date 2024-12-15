package com.threeping.syncday.workspace.command.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.workspace.command.aggregate.entity.VcsType;
import lombok.Data;
import lombok.Value;

import java.sql.Timestamp;
@Data
@Value
public class WorkspaceVO {
    /* Todo: 유효성 검사를 위해 유저아이디를 추가하여 VO로 생성 */

    @JsonProperty("workspace_id")
    private Long workspaceId;

    @JsonProperty("workspace_name")
    private String workspaceName;

    @JsonProperty("vcs_type")
    private VcsType vcsType;

    @JsonProperty("vcs_repo_name")
    private String vcsRepoName;

    @JsonProperty("vcs_repo_url")
    private String vcsRepoUrl;
}
