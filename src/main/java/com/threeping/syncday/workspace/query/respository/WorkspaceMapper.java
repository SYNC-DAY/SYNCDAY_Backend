package com.threeping.syncday.workspace.query.respository;


import com.threeping.syncday.workspace.query.aggregate.WorkspaceDTO;
import com.threeping.syncday.workspace.query.aggregate.WorkspaceInfoDTO;
import com.threeping.syncday.workspace.query.dto.WorkspaceQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkspaceMapper {

    List<WorkspaceDTO> selectAllWorkSpaces();
    WorkspaceQueryDTO findWorkspaceById(Long workspaceId);
    List<WorkspaceDTO> selectWorkspacesByProjId(Long projId);
    WorkspaceInfoDTO selectWorkspaceById(@Param("workspaceId") Long workspaceId);
    List<WorkspaceQueryDTO> findAllWorkspaces();
}
