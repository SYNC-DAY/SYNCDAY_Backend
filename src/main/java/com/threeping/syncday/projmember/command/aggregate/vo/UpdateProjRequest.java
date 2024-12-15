package com.threeping.syncday.projmember.command.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.proj.command.aggregate.entity.VcsType;

import java.sql.Timestamp;

public record UpdateProjRequest(
        @JsonProperty("proj_member_id") Long projMemberId,
        @JsonProperty("proj_id") Long projId,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("proj_name") String projName,
        @JsonProperty("start_time") Timestamp startTime,
        @JsonProperty("end_time") Timestamp endTime,
        @JsonProperty("created_at") Timestamp createdAt,
        @JsonProperty("vcs_type") VcsType vcsType,
        @JsonProperty("vcs_proj_url") String vcsProjUrl,
        @JsonProperty("github_installation_id") Long githubInstallationId) {

}
