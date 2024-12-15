package com.threeping.syncday.proj.query.aggregate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.workspace.query.aggregate.WorkspaceDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class  ProjAndWorkspaceDTO {

    @JsonProperty("proj_id")
    Long projId;

    @JsonProperty("proj_name")
    String projName;

    @JsonProperty("start_time")
    Timestamp startTime;

    @JsonProperty("end_time")
    Timestamp endTime;

    @JsonProperty("created_at")
    Timestamp createdAt;

    @JsonProperty("progress_status")
    Byte progressStatus;


    @JsonProperty("vcs_proj_url")
    String vcsProjUrl;

    @JsonProperty("workspaces")
    List<WorkspaceDTO> workspaces;
}