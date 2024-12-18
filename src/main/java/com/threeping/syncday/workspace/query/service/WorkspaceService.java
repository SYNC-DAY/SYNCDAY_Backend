package com.threeping.syncday.workspace.query.service;

import com.threeping.syncday.workspace.query.aggregate.dto.WorkspaceDTO;

import java.util.List;

public interface WorkspaceService {
    List<WorkspaceDTO> getWorkspacesByProjIds(List<Long> projIds);
}
