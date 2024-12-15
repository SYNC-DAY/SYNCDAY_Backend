package com.threeping.syncday.projmember.command.infrastructure.service;

import com.threeping.syncday.proj.command.aggregate.dto.ProjDTO;
import com.threeping.syncday.proj.command.application.service.AppProjService;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjRequest;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateWorkspaceRequest;
import com.threeping.syncday.workspace.command.aggregate.dto.WorkspaceDTO;
import com.threeping.syncday.workspace.command.aggregate.vo.WorkspaceVO;
import com.threeping.syncday.workspace.command.application.service.AppWorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class InfraProjMemberServiceImpl implements InfraProjMemberService {
    private final AppProjService appProjService;
    private final AppWorkspaceService appWorkspaceService;

    @Override
    public ProjDTO requestAddProj(UpdateProjRequest req) {
        return appProjService.addProj(req);
    }

    @Override
    public ProjDTO requestUpdateProj(UpdateProjRequest req) {

        return appProjService.updateProj(req);
    }

    @Override
    public WorkspaceDTO requestUpdateWorkspace(WorkspaceVO workspaceVO) {

        return appWorkspaceService.modifyWorkspace(workspaceVO);
    }
}
