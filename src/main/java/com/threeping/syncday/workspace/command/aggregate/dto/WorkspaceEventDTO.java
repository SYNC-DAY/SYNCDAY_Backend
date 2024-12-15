package com.threeping.syncday.workspace.command.aggregate.dto;

import com.threeping.syncday.workspace.query.dto.WorkspaceQueryDTO;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Builder
public class WorkspaceEventDTO {
    private Long workspaceId;
    private String workspaceName;
    private Long projectId;
    private String projectName;
    private String vcsType;
    private String vcsRepoName;
    private String vcsRepoUrl;
    private LocalDateTime createdAt;
    private String eventType;

    public static WorkspaceEventDTO from(WorkspaceQueryDTO workspaceInfo) {
        return WorkspaceEventDTO.builder()
                .workspaceId(workspaceInfo.getWorkspaceId())
                .workspaceName(workspaceInfo.getWorkspaceName())
                .projectId(workspaceInfo.getProjectId())
                .projectName(workspaceInfo.getProjectName())
                .vcsType(workspaceInfo.getVcsType())
                .vcsRepoName(workspaceInfo.getVcsRepoName())
                .vcsRepoUrl(workspaceInfo.getVcsRepoUrl())
                .createdAt(convertToLocalDateTime(workspaceInfo.getCreatedAt()))
                .eventType("CREATE")
                .build();
    }

    public static WorkspaceEventDTO createDeleted(WorkspaceQueryDTO workspaceInfo) {
        return WorkspaceEventDTO.builder()
                .workspaceId(workspaceInfo.getWorkspaceId())
                .workspaceName(workspaceInfo.getWorkspaceName())
                .projectId(workspaceInfo.getProjectId())
                .projectName(workspaceInfo.getProjectName())
                .vcsType(workspaceInfo.getVcsType())
                .vcsRepoName(workspaceInfo.getVcsRepoName())
                .vcsRepoUrl(workspaceInfo.getVcsRepoUrl())
                .createdAt(convertToLocalDateTime(workspaceInfo.getCreatedAt()))
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
