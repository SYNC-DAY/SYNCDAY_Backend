package com.threeping.syncday.card.query.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TodayCardDTO {
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

    @JsonProperty("vcs_object_Type")
    private String vcsObjectType;

    @JsonProperty("vcs_object_url")
    private String vcsObjectUrl;

    @JsonProperty("cardboard_id")
    private Long cardBoardId;

    @JsonProperty("tag_id")
    private Long tagId;

    @JsonProperty("color")
    private String color;

    @JsonProperty("tag_name")
    private String tagName;

    @JsonProperty("created_by")
    private Long createdBy;

    @JsonProperty("assignee")
    private Long assignee;

}
