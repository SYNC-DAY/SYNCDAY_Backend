package com.threeping.syncday.workspace.query.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.workspace.query.aggregate.dto.WorkspaceDTO;
import com.threeping.syncday.workspace.query.repository.WorkspaceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {
    private final WorkspaceMapper workspaceMapper;

    @Override
    public List<WorkspaceDTO> getWorkspacesByProjIds(List<Long> projIds) {
            if(projIds == null || projIds.isEmpty()) {
                throw new CommonException(ErrorCode.EMPTY_PROJ_LIST);
            }
        return workspaceMapper.selectWorkspacesByProjectIds(projIds);
    }
}
