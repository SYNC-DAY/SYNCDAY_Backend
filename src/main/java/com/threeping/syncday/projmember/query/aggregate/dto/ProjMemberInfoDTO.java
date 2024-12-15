package com.threeping.syncday.projmember.query.aggregate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.workspace.query.aggregate.WorkspaceDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ProjMemberInfoDTO {

    /* projMemberInfo */
    @JsonProperty("proj_member_id")
    private Long projMemberId;

    @JsonProperty("bookmark_status")
    private String bookmarkStatus;

    @JsonProperty("participation_status")
    private String participationStatus;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("proj_id")
    private Long projId;

    /* project Infos */
    @JsonProperty("proj_name")
    private String projName;

    @JsonProperty("start_time")
    private Timestamp startTime;

    @JsonProperty("end_time")
    private Timestamp endTime;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    @JsonProperty("progress_status")
    private Byte progressStatus;

    @JsonProperty("vcs_type")
    private String vcsType;
    @JsonProperty("vcs_proj_url")
    private String vcsProjUrl;

    @JsonProperty("vcs_installation_id")
    private Long vcsInstallationId;
    @JsonProperty("workspaces")
    List<WorkspaceDTO> workspaces;

}