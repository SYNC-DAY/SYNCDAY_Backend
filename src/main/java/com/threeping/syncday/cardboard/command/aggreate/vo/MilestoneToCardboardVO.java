package com.threeping.syncday.cardboard.command.aggreate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.cardboard.command.aggreate.entity.VcsType;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class MilestoneToCardboardVO {

    @JsonProperty("cardboard_id")
    private Long cardboardId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("vcs_type")

    private VcsType vcsType;
    @JsonProperty("vcs_milestone_url")

    private String vcsMilestoneUrl;
    @JsonProperty("progress_status")

    private Byte progressStatus;
    @JsonProperty("workspace_id")

    private Long workspaceId;
    @JsonProperty("start_time")

    private Timestamp startTime;
    @JsonProperty("end_time")

    private Timestamp endTime;
    private Timestamp createTime;

    private List<IssueToCardVO> cards;

}
