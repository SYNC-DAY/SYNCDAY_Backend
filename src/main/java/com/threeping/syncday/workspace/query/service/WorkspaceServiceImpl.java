package com.threeping.syncday.workspace.query.service;


import com.threeping.syncday.workspace.query.aggregate.WorkspaceDTO;
import com.threeping.syncday.workspace.query.aggregate.WorkspaceInfoDTO;
import com.threeping.syncday.workspace.query.respository.WorkspaceMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceMapper workspaceMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceMapper workspaceMapper
            , ModelMapper modelMapper) {
        this.workspaceMapper = workspaceMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<WorkspaceDTO> getAllWorkspaces() {
        return workspaceMapper.selectAllWorkSpaces();
    }

    @Override
    public List<WorkspaceDTO> getWorkspacesByProjId(Long projId) {
        List<WorkspaceDTO> workspaces = workspaceMapper.selectWorkspacesByProjId(projId);
        workspaces.forEach(x-> log.info("x: {}", x));
       return workspaceMapper.selectWorkspacesByProjId(projId);
    }

    @Override
    public WorkspaceInfoDTO getWorkspaceInfo(Long workspaceId) {
        WorkspaceInfoDTO workspace = workspaceMapper.selectWorkspaceById(workspaceId);
        log.info("workspace: {}", workspace);
        return workspaceMapper.selectWorkspaceById(workspaceId);
    }
}
