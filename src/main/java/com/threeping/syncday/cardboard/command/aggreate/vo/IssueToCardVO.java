package com.threeping.syncday.cardboard.command.aggreate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.card.command.aggregate.entity.VCSOBJECTTYPE;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class IssueToCardVO {

    @JsonProperty("user_id")
    private Long createdBy;

    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("vcs_object_url")
    private String vcsObjectUrl;

    @JsonProperty("vcs_object_type")
    private VCSOBJECTTYPE vcsobjecttype = VCSOBJECTTYPE.ISSUE;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    @JsonProperty("updated_at")
    private Timestamp updatedAt;

    @JsonProperty("status")
    private String status;


}
