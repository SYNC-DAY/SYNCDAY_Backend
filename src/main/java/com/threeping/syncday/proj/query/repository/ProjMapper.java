package com.threeping.syncday.proj.query.repository;

import com.threeping.syncday.proj.query.aggregate.dto.ProjDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjMapper {
    List<ProjDTO> selectAllProjs();
    ProjDTO selectProjById(Long projId);
}
