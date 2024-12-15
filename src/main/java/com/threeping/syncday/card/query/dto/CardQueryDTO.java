package com.threeping.syncday.card.query.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class CardQueryDTO {
    private Long cardId;
    private String cardTitle;
    private String cardContent;
    private Long cardboardId;
    private String cardboardName;
    private Long workspaceId;
    private String workspaceName;
    private Long projectId;
    private Long creatorId;
    private String creatorName;
    private Long assigneeId;
    private String assigneeName;
    private String tags;
    private String vcsObject;
    private String  vcsUrl;
    private Timestamp createdAt;
}
