package com.threeping.syncday.proj.query.repository;

import com.threeping.syncday.proj.query.aggregate.dto.ProjAndWorkspaceDTO;
import com.threeping.syncday.proj.query.aggregate.dto.ProjDTO;
import com.threeping.syncday.proj.query.aggregate.dto.ProjectQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjMapper {
    List<ProjDTO> selectAllProjs();


    ProjDTO selectProjById(Long projId);


    ProjectQueryDTO findProjById(Long projId);

    List<ProjectQueryDTO> findAllProjs();
}
