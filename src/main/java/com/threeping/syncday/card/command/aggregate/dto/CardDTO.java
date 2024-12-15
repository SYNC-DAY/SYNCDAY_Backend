package com.threeping.syncday.card.command.aggregate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.card.command.aggregate.entity.VCSOBJECTTYPE;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class CardDTO {

    @JsonProperty("card_id")
    private Long cardId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    @JsonProperty("start_time")
    private Timestamp startTime;

    @JsonProperty("end_time")
    private Timestamp endTime;

    @JsonProperty("vsc_object_type")
    private VCSOBJECTTYPE vcsObjectType;

    @JsonProperty("vcs_object_url")
    private String vcsObjectUrl;

    @JsonProperty("cardboard_id")
    private Long cardboardId;

    @JsonProperty("tag_id")
    private Long tagId;

    @JsonProperty("created_by")
    private Long createdBy;

    @JsonProperty("assignee")
    private Long assignee;

}
