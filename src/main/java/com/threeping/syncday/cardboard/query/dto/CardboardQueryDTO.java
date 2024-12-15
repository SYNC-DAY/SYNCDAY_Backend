package com.threeping.syncday.cardboard.query.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CardboardQueryDTO {
    private Long cardboardId;
    private String cardboardName;
    private Long projectId;
    private Long workspaceId;
    private String workspaceName;
    private String vcsType;
    private String vcsMilestoneUrl;
    private Timestamp createdAt;
}
