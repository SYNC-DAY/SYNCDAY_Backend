package com.threeping.syncday.workspace.query.service;


import com.threeping.syncday.workspace.query.aggregate.WorkspaceDTO;
import com.threeping.syncday.workspace.query.aggregate.WorkspaceInfoDTO;

import java.util.List;

public interface WorkspaceService {
    List<WorkspaceDTO> getAllWorkspaces();

    List<WorkspaceDTO> getWorkspacesByProjId(Long projId);

    WorkspaceInfoDTO getWorkspaceInfo(Long workspaceId);

}
