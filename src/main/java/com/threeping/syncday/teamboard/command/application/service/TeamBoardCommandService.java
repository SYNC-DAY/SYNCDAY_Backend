package com.threeping.syncday.teamboard.command.application.service;

import com.threeping.syncday.teamboard.command.aggregate.dto.TeamBoardDTO;

public interface TeamBoardCommandService {
    TeamBoardDTO createTeamBoard(TeamBoardDTO teamBoardDTO);

    TeamBoardDTO updateTeamBoard(TeamBoardDTO teamBoardDTO);

    TeamBoardDTO deleteTeamBoard(TeamBoardDTO teamBoardDTO);
}
