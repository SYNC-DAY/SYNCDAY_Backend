package com.threeping.syncday.projmember.command.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.proj.command.aggregate.entity.VcsType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RequestUpdateVcsInfoVO {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("proj_id")
    private Long projId;

    @JsonProperty("vcs_type")
    private VcsType vcsType;
    @JsonProperty("vcs_proj_url")
    private String vcsProjUrl;
}
