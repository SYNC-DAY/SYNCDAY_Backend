package com.threeping.syncday.teamboard.query.service;

import com.threeping.syncday.teamboard.command.aggregate.dto.TeamBoardDTO;
import com.threeping.syncday.teamboard.query.repository.TeamBoardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamBoardQueryServiceImpl implements TeamBoardQueryService{

    private final TeamBoardMapper teamBoardMapper;

    public TeamBoardQueryServiceImpl(TeamBoardMapper teamBoardMapper) {
        this.teamBoardMapper = teamBoardMapper;
    }

    @Override
    public List<TeamBoardDTO> findMyTeamBoard(Long userId) {
        return teamBoardMapper.findMyTeamBoardByUserId(userId);
    }
}
