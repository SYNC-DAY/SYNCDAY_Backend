package com.threeping.syncday.workspace.query.service;

import com.threeping.syncday.workspace.infrastructure.elasticsearch.document.WorkSpaceDocument;
import com.threeping.syncday.workspace.infrastructure.elasticsearch.repository.WorkSpaceSearchRepository;
import com.threeping.syncday.workspace.query.dto.WorkspaceSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkspaceSearchServiceImpl implements WorkspaceSearchService {

    private final WorkSpaceSearchRepository workSpaceSearchRepository;

    @Autowired
    public WorkspaceSearchServiceImpl(WorkSpaceSearchRepository workSpaceSearchRepository) {
        this.workSpaceSearchRepository = workSpaceSearchRepository;
    }

    @Override
    public List<WorkspaceSearchResponse> searchWorkspace(String keyword) {
        log.info("워크스페이스 검색으로 들어온 키워드: {}", keyword);

        List<WorkspaceSearchResponse> responseList = workSpaceSearchRepository
                .searchByKeyword(keyword).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return responseList;
    }

    private WorkspaceSearchResponse convertToResponse(WorkSpaceDocument doc) {
        return WorkspaceSearchResponse
                .builder()
                .workspaceId(doc.getWorkspaceId())
                .workspaceName(doc.getWorkspaceName())
                .projectId(doc.getProjectId())
                .projectName(doc.getProjectName())
                .vcsType(doc.getVcsType())
                .vcsRepoName(doc.getVcsRepoName())
                .vcsRepoUrl(doc.getVcsRepoUrl())
                .createdAt((doc.getCreatedAt()))
                .build();
    }
}
