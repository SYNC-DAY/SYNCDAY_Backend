package com.threeping.syncday.proj.query.aggregate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ProjectSearchResponse {
    @Schema(description = "프로젝트 고유 번호(PK)", example = "9", type = "Long")
    private Long projectId;
    @Schema(description = "프로젝트 이름", example = "신규 서비스 개발")
    private String projectName;
    @Schema(description = "VCS 종류", example = "GITLAB")
    private String vcsType;
    @Schema(description = "프로젝트 생성일자", example = "2024-11-11")
    private LocalDateTime createdAt;
}
