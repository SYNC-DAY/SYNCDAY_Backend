package com.threeping.syncday.workspace.query.config;

import com.threeping.syncday.workspace.command.aggregate.dto.WorkspaceEventDTO;
import com.threeping.syncday.workspace.command.aggregate.entity.Workspace;
import com.threeping.syncday.workspace.query.dto.WorkspaceQueryDTO;
import com.threeping.syncday.workspace.query.respository.WorkspaceMapper;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceEntityListener {

    private final ApplicationEventPublisher eventPublisher;
    private final WorkspaceMapper workspaceMapper;

    @Autowired
    public WorkspaceEntityListener(ApplicationEventPublisher eventPublisher, WorkspaceMapper workspaceMapper) {
        this.eventPublisher = eventPublisher;
        this.workspaceMapper = workspaceMapper;
    }

    @PostPersist
    @PostUpdate
    public void onPostPersistOrUpdate(Workspace workspace) {
        WorkspaceQueryDTO workspaceInfo = workspaceMapper.findWorkspaceById(workspace.getWorkspaceId());

        eventPublisher.publishEvent(WorkspaceEventDTO.from(workspaceInfo));
    }

    @PreRemove
    public void onPreRemove(Workspace workspace) {
        WorkspaceQueryDTO workspaceInfo = workspaceMapper.findWorkspaceById(workspace.getWorkspaceId());

        eventPublisher.publishEvent(WorkspaceEventDTO.createDeleted(workspaceInfo));
    }
}
