package com.threeping.syncday.projmember.command.infrastructure.service;


import com.threeping.syncday.proj.command.aggregate.dto.ProjDTO;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjRequest;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateWorkspaceRequest;
import com.threeping.syncday.workspace.command.aggregate.dto.WorkspaceDTO;
import com.threeping.syncday.workspace.command.aggregate.vo.WorkspaceVO;

public interface InfraProjMemberService {
    ProjDTO requestAddProj(UpdateProjRequest req);

    ProjDTO requestUpdateProj(UpdateProjRequest req);

    WorkspaceDTO requestUpdateWorkspace(WorkspaceVO workspaceVO);
}
