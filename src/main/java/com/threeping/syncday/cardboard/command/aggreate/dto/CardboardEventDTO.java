package com.threeping.syncday.cardboard.command.aggreate.dto;

import com.threeping.syncday.cardboard.query.dto.CardboardQueryDTO;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Builder
public class CardboardEventDTO {

    private Long cardboardId;
    private String cardboardName;
    private Long projectId;
    private Long workspaceId;
    private String workspaceName;
    private String vcsType;
    private String vcsMilestoneUrl;
    private LocalDateTime createdAt;
    private String eventType;

    public static CardboardEventDTO from(CardboardQueryDTO cardboardInfo) {
        return CardboardEventDTO
                .builder()
                .cardboardId(cardboardInfo.getCardboardId())
                .cardboardName(cardboardInfo.getCardboardName())
                .projectId(cardboardInfo.getProjectId())
                .workspaceId(cardboardInfo.getWorkspaceId())
                .workspaceName(cardboardInfo.getWorkspaceName())
                .vcsType(cardboardInfo.getVcsType())
                .vcsMilestoneUrl(cardboardInfo.getVcsMilestoneUrl())
                .createdAt(convertToLocalDateTime(cardboardInfo.getCreatedAt()))
                .eventType("CREATE")
                .build();
    }

    public static CardboardEventDTO createDeleted(CardboardQueryDTO cardboardInfo) {
        return CardboardEventDTO
                .builder()
                .cardboardId(cardboardInfo.getCardboardId())
                .cardboardName(cardboardInfo.getCardboardName())
                .workspaceId(cardboardInfo.getWorkspaceId())
                .workspaceName(cardboardInfo.getWorkspaceName())
                .vcsType(cardboardInfo.getVcsType())
                .vcsMilestoneUrl(cardboardInfo.getVcsMilestoneUrl())
                .createdAt(convertToLocalDateTime(cardboardInfo.getCreatedAt()))
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
