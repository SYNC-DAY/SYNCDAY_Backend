package com.threeping.syncday.card.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Builder
@ToString
public class CardSearchResponse {
    @Schema(description = "카드 고유 번호(PK)", example = "1", type = "Long")
    private Long cardId;
    @Schema(description = "카드 이름", example = "스프린트1")
    private String cardTitle;
    @Schema(description = "카드 내용", example = "스프린트1 개발 시작!")
    private String cardContent;
    @Schema(description = "카드보드 고유 번호(FK)", example = "1", type = "Long")
    private Long cardboardId;
    @Schema(description = "카드보드 이름", example = "스프린트1")
    private String cardboardName;
    @Schema(description = "워크스페이스 고유 번호(FK)", example = "1", type = "Long")
    private Long workspaceId;
    @Schema(description = "워크스페이스 이름", example = "SyncDay 백엔드")
    private String workspaceName;
    @Schema(description = "프로젝트 고유 번호(FK)", example = "1", type = "Long")
    private Long projectId;
    @Schema(description = "생성자 고유 번호(FK)", example = "1", type = "Long")
    private Long creatorId;
    @Schema(description = "생성자 이름", example = "김개발")
    private String creatorName;
    @Schema(description = "할당자 고유 번호(FK)", example = "1", type = "Long")
    private Long assigneeId;
    @Schema(description = "할당자 이름", example = "장그래")
    private String assigneeName;
    @Schema(description = "태그 이름", example = "긴급")
    private String tags;
    @Schema(description = "vcs 객체", example = "Commit")
    private String vcsObject;
    @Schema(description = "vcs Url 주소", example = "https://github.com/three-ping/SyncDay_Back/commit/5b4bc8f7bb6b7435a7663a532c6d79086062ae8e")
    private String  vcsUrl;
    @Schema(description = "생성일자", example = "20241101")
    private LocalDateTime createdAt;
}
