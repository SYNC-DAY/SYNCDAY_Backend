package com.threeping.syncday.teamboard.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.teamboard.command.aggregate.dto.TeamBoardDTO;
import com.threeping.syncday.teamboard.command.aggregate.entity.TeamBoard;
import com.threeping.syncday.teamboard.command.domain.repository.TeamBoardRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamBoardCommandServiceImpl implements TeamBoardCommandService{

    private final TeamBoardRepository teamBoardRepository;

    public TeamBoardCommandServiceImpl(TeamBoardRepository teamBoardRepository){
        this.teamBoardRepository = teamBoardRepository;
    }

    @Override
    public TeamBoardDTO createTeamBoard(TeamBoardDTO teamBoardDTO) {
        TeamBoard teamBoard = new TeamBoard();
        teamBoard.setTeamId(teamBoardDTO.getTeamId());
        teamBoard.setBoardTitle(teamBoardDTO.getBoardTitle());

        teamBoardRepository.save(teamBoard);

        teamBoardDTO.setTeamBoardId(teamBoard.getTeamBoardId());

        return teamBoardDTO;
    }

    @Override
    public TeamBoardDTO updateTeamBoard(TeamBoardDTO teamBoardDTO) {
        TeamBoard teamBoard = teamBoardRepository.findById(teamBoardDTO.getTeamBoardId())
                .orElseThrow(
                        ()->new CommonException(ErrorCode.INVALID_INPUT_VALUE)
                );
        teamBoard.setBoardTitle(teamBoardDTO.getBoardTitle());

        teamBoardRepository.save(teamBoard);
        teamBoardDTO.setTeamId(teamBoard.getTeamId());

        return teamBoardDTO;
    }

    @Override
    public TeamBoardDTO deleteTeamBoard(TeamBoardDTO teamBoardDTO) {
        TeamBoard teamBoard = teamBoardRepository.findById(teamBoardDTO.getTeamBoardId())
                .orElseThrow(
                        ()->new CommonException(ErrorCode.INVALID_INPUT_VALUE)
                );
        teamBoardDTO.setTeamId(teamBoard.getTeamId());
        teamBoardDTO.setBoardTitle(teamBoard.getBoardTitle());

        teamBoardRepository.delete(teamBoard);
        return teamBoardDTO;
    }
}
