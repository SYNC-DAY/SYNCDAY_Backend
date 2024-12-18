package com.threeping.syncday.projmember.query.aggregate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.proj.query.aggregate.dto.ProjDTO;
import lombok.Data;

@Data
public class ProjMemberDTO {

    @JsonProperty("proj_member_id")
    private Long projMemberId;

    @JsonProperty("bookmark_status")
    private String bookmarkStatus;

    @JsonProperty("participation_status")
    private String participationStatus;

    @JsonProperty("proj_id")
    private Long projId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("proj")
    ProjDTO proj;
}
