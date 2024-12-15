package com.threeping.syncday.workspace.query.config.elasticsearch;

import com.threeping.syncday.workspace.command.aggregate.dto.WorkspaceEventDTO;
import com.threeping.syncday.workspace.infrastructure.elasticsearch.document.WorkSpaceDocument;
import com.threeping.syncday.workspace.infrastructure.elasticsearch.repository.WorkSpaceSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class WorkspaceSearchEventHandler {

    private final WorkSpaceSearchRepository searchRepository;

    @Autowired
    public WorkspaceSearchEventHandler(WorkSpaceSearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @EventListener
    public void HandleProEvent(WorkspaceEventDTO dto) {
        WorkSpaceDocument doc = WorkSpaceDocument
                .builder()
                .workspaceId(dto.getWorkspaceId())
                .workspaceName(dto.getWorkspaceName())
                .projectId(dto.getProjectId())
                .projectName(dto.getProjectName())
                .vcsType(dto.getVcsType())
                .vcsRepoName(dto.getVcsRepoName())
                .vcsRepoUrl(dto.getVcsRepoUrl())
                .createdAt(dto.getCreatedAt())
                .build();

        switch (dto.getEventType()) {
            case "CREATE" -> searchRepository.save(doc);
            case "DELETE" -> searchRepository.delete(doc);
            default -> throw new IllegalArgumentException("Unknown event type: " + dto.getEventType());
        }
    }
}
