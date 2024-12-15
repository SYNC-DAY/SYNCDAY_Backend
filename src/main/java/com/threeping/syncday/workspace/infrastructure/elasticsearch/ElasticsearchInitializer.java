package com.threeping.syncday.workspace.infrastructure.elasticsearch;

import com.threeping.syncday.user.infrastructure.elasticsearch.document.UserSearchDocument;
import com.threeping.syncday.workspace.infrastructure.elasticsearch.document.WorkSpaceDocument;
import com.threeping.syncday.workspace.infrastructure.elasticsearch.repository.WorkSpaceSearchRepository;
import com.threeping.syncday.workspace.query.dto.WorkspaceQueryDTO;
import com.threeping.syncday.workspace.query.respository.WorkspaceMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component("workspace_init")
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchInitializer {
    private final WorkspaceMapper workspaceMapper;
    private final WorkSpaceSearchRepository searchRepository;

    @PostConstruct
    public void init() {
        log.info("워크 스페이스 ES 동기화 시작!");
        synchronizeAll();
    }

    // 수동 동기화 메서드 추가
    public void synchronizeAll() {
        searchRepository.deleteAll();

        List<WorkspaceQueryDTO> workspaces = workspaceMapper.findAllWorkspaces();
        log.info("db에서 저장된 유저 수: ", workspaces.size());

                workspaces.forEach(workspaceDTO -> {
                    WorkSpaceDocument document = WorkSpaceDocument.builder()
                            .workspaceId(workspaceDTO.getWorkspaceId())
                            .workspaceName(workspaceDTO.getWorkspaceName())
                            .projectId(workspaceDTO.getProjectId())
                            .projectName(workspaceDTO.getProjectName())
                            .vcsType(workspaceDTO.getVcsType())
                            .vcsRepoName(workspaceDTO.getVcsRepoName())
                            .vcsRepoUrl(workspaceDTO.getVcsRepoUrl())
                            .createdAt(convertToLocalDateTime(workspaceDTO.getCreatedAt()))
                            .build();
                    searchRepository.save(document);
                });
    }

    private static LocalDateTime convertToLocalDateTime(Timestamp createdAt) {
        if (createdAt == null) {
            return null;
        }
        return createdAt.toLocalDateTime();
    }
}