package com.threeping.syncday.cardboard.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Builder
@ToString
public class CardboardSearchResponse {
    @Schema(description = "카드보드 고유 번호(PK)", example = "1", type = "Long")
    private Long cardboardId;
    @Schema(description = "카드보드 이름", example = "스프린트1")
    private String cardboardName;
    @Schema(description = "프로젝트 고유 번호(FK)", example = "1", type = "Long")
    private Long projectId;
    @Schema(description = "워크스페이스 고유 번호(FK)", example = "1", type = "Long")
    private Long workspaceId;
    @Schema(description = "워크스페이스 이름", example = "SyncDay 백엔드")
    private String workspaceName;
    @Schema(description = "VCS 타입", example = "GITHUB")
    private String vcsType;
    @Schema(description = "VCS 마일스톤 URL", example = "https://github.com/three-ping/SyncDay_Front/milestone/6")
    private String vcsMilestoneUrl;
    @Schema(description = "생성일자", example = "20241121")
    private LocalDateTime createdAt;
}
