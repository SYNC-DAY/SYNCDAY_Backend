package com.threeping.syncday.workspace.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.workspace.command.aggregate.dto.WorkspaceDTO;
import com.threeping.syncday.workspace.command.aggregate.vo.WorkspaceVO;
import com.threeping.syncday.workspace.command.aggregate.entity.Workspace;
import com.threeping.syncday.workspace.command.domain.repository.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AppWorkspaceServiceImpl implements AppWorkspaceService {

    private final ModelMapper modelMapper;
    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public AppWorkspaceServiceImpl(ModelMapper modelMapper, WorkspaceRepository workspaceRepository) {
        this.modelMapper = modelMapper;
        this.workspaceRepository = workspaceRepository;
    }

    
    /* Todo: 워크스페이스 생성 권한 확인 */
    @Transactional
    @Override
    public WorkspaceVO addWorkspace(WorkspaceVO newWorkspace) {
        Workspace workspaceToAdd = modelMapper.map(newWorkspace, Workspace.class);
        log.info("workspaceToAdd: {}", workspaceToAdd);
        Workspace addedWorkspace = workspaceRepository.save(workspaceToAdd);
        log.info("addedWorkspace: {}", addedWorkspace);

        return modelMapper.map(addedWorkspace, WorkspaceVO.class);
    }

    @Transactional
    @Override
    public WorkspaceDTO modifyWorkspace(WorkspaceVO workspaceVO) {
        Workspace existingWs = workspaceRepository.findById(workspaceVO.getWorkspaceId()).orElse(null);
        if (existingWs == null) {
            throw new CommonException(ErrorCode.WORKSPACE_NOT_FOUND);
        }
        existingWs.setWorkspaceName(workspaceVO.getWorkspaceName());
        existingWs.setVcsType(workspaceVO.getVcsType());
        existingWs.setVcsRepoName(workspaceVO.getVcsRepoName());
        existingWs.setVcsRepoUrl(workspaceVO.getVcsRepoUrl());
        Workspace updatedWorkspace = workspaceRepository.save(existingWs);
        log.info("updatedWorkspace: {}", updatedWorkspace);
        return modelMapper.map(updatedWorkspace, WorkspaceDTO.class);
    }

    @Transactional
    @Override
    public WorkspaceDTO deleteWorkspace(Long workspaceId) {
        Workspace existingWorkspace = workspaceRepository.findById(workspaceId).orElse(null);

        if (existingWorkspace == null) {
            throw new CommonException(ErrorCode.WORKSPACE_NOT_FOUND);
        }

        workspaceRepository.delete(existingWorkspace);
        return modelMapper.map(existingWorkspace, WorkspaceDTO.class);
    }
}
