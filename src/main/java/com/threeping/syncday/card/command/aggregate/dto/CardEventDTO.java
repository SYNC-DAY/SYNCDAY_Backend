package com.threeping.syncday.card.command.aggregate.dto;

import com.threeping.syncday.card.query.dto.CardQueryDTO;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Builder
public class CardEventDTO {
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
    private LocalDateTime createdAt;
    private String eventType;

    public static CardEventDTO from(CardQueryDTO cardInfo) {
        return CardEventDTO
                .builder()
                .cardId(cardInfo.getCardId())
                .cardTitle(cardInfo.getCardTitle())
                .cardContent(cardInfo.getCardContent())
                .cardboardId(cardInfo.getCardboardId())
                .cardboardName(cardInfo.getCardboardName())
                .workspaceId(cardInfo.getWorkspaceId())
                .workspaceName(cardInfo.getWorkspaceName())
                .projectId(cardInfo.getProjectId())
                .creatorId(cardInfo.getCreatorId())
                .creatorName(cardInfo.getCreatorName())
                .assigneeId(cardInfo.getAssigneeId())
                .assigneeName(cardInfo.getAssigneeName())
                .tags(cardInfo.getTags())
                .vcsObject(cardInfo.getVcsObject())
                .vcsUrl(cardInfo.getVcsUrl())
                .createdAt(convertToLocalDateTime(cardInfo.getCreatedAt()))
                .eventType("CREATE")
                .build();
    }

    public static CardEventDTO createDeleted(CardQueryDTO cardInfo) {
        return CardEventDTO
                .builder()
                .cardId(cardInfo.getCardId())
                .cardTitle(cardInfo.getCardTitle())
                .cardContent(cardInfo.getCardContent())
                .cardboardId(cardInfo.getCardboardId())
                .cardboardName(cardInfo.getCardboardName())
                .workspaceId(cardInfo.getWorkspaceId())
                .workspaceName(cardInfo.getWorkspaceName())
                .projectId(cardInfo.getProjectId())
                .creatorId(cardInfo.getCreatorId())
                .creatorName(cardInfo.getCreatorName())
                .assigneeId(cardInfo.getAssigneeId())
                .assigneeName(cardInfo.getAssigneeName())
                .tags(cardInfo.getTags())
                .vcsObject(cardInfo.getVcsObject())
                .vcsUrl(cardInfo.getVcsUrl())
                .createdAt(convertToLocalDateTime(cardInfo.getCreatedAt()))
                .eventType("DELETE")
                .build();
    }


    private static LocalDateTime convertToLocalDateTime(Timestamp createdAt) {
        if (createdAt == null) {
            return null;
        }
        return createdAt.toLocalDateTime();
    }
}
