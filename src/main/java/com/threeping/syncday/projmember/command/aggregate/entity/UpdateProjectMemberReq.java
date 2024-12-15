package com.threeping.syncday.projmember.command.aggregate.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UpdateProjectMemberReq {

    /* 요청자의 userId */
    @JsonProperty("user_id")
     Long userId;

    @JsonProperty("proj_id")
     Long projId;

    /* 생성, 수정, 삭제할 사람의 userId */
    @JsonProperty("user_to_update")
    Long userToUpdate;
}
