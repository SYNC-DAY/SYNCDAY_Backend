package com.threeping.syncday.teamboard.query.service;

import com.threeping.syncday.teamboard.command.aggregate.dto.TeamBoardDTO;

import java.util.List;

public interface TeamBoardQueryService {
    List<TeamBoardDTO> findMyTeamBoard(Long userId);
}
