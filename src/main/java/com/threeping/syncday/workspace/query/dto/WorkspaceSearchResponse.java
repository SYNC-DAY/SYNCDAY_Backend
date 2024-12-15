package com.threeping.syncday.workspace.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class WorkspaceSearchResponse {
    @Schema(description = "워크스페이스 고유 번호(PK)", example = "1", type = "Long")
    private Long workspaceId;
    @Schema(description = "워크스페이스 이름", example = "백엔드 개발")
    private String workspaceName;
    @Schema(description = "프로젝트 고유 번호(FK)", example = "1", type = "Long")
    private Long projectId;
    @Schema(description = "프로젝트 이름", example = "SyncDay 웹 개발")
    private String projectName;
    @Schema(description = "VCS TYPE", example = "GITHUB")
    private String vcsType;
    @Schema(description = "vcs Url", example = "https://github.com/syncday/backend/refactoring")
    private String vcsRepoUrl;
    @Schema(description = "vcs 레포지토리 이름", example = "SyncDay_Refactoring")
    private String vcsRepoName;
    @Schema(description = "생성일자", example = "20241121")
    private LocalDateTime createdAt;
}
