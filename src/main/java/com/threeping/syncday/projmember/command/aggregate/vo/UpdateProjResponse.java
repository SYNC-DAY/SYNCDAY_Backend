package com.threeping.syncday.projmember.command.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public record UpdateProjResponse(@JsonProperty("proj_id") Long projId,
                                 @JsonProperty("proj_member_id") Long projMemberId,
                                 @JsonProperty("proj_name") String projName,
                                 @JsonProperty("start_time") Timestamp startTime,
                                 @JsonProperty("end_time") Timestamp endTime,
                                 @JsonProperty("vcs_type") String vcsType,
                                 @JsonProperty("vcs_proj_url") String vcsProjUrl,
                                 @JsonProperty("github_installation_id")Long githubInstallationId) {

}
