package com.threeping.syncday.workspace.query.repository;

import com.threeping.syncday.workspace.query.aggregate.dto.WorkspaceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkspaceMapper {
    List<WorkspaceDTO> selectWorkspacesByProjectIds(List<Long> projIds);
}
