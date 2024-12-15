package com.threeping.syncday.projmember.query.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProjMemberDTO {

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

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String userName;

}
