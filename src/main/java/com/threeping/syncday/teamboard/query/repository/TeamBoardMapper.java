package com.threeping.syncday.teamboard.query.repository;

import com.threeping.syncday.teamboard.command.aggregate.dto.TeamBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamBoardMapper {
    List<TeamBoardDTO> findMyTeamBoardByUserId(Long userId);
}
