package com.threeping.syncday.workspace.command.application.service;

import com.threeping.syncday.workspace.command.aggregate.dto.WorkspaceDTO;
import com.threeping.syncday.workspace.command.aggregate.vo.WorkspaceVO;

public interface AppWorkspaceService {
    WorkspaceVO addWorkspace(WorkspaceVO newWorkspace);

    WorkspaceDTO modifyWorkspace(WorkspaceVO workspaceVO);

    WorkspaceDTO deleteWorkspace(Long workspaceId);
}
