package com.threeping.syncday.teamboard.command.application.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.teamboard.command.aggregate.dto.TeamBoardDTO;
import com.threeping.syncday.teamboard.command.application.service.TeamBoardCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teamboard")
public class TeamBoardCommandController {

    private final TeamBoardCommandService teamBoardCommandService;

    @Autowired
    public TeamBoardCommandController(TeamBoardCommandService teamBoardCommandService){
        this.teamBoardCommandService = teamBoardCommandService;
    }

    @PostMapping("")
    public ResponseDTO<?> createTeamBoard(@RequestBody TeamBoardDTO teamBoardDTO){
        TeamBoardDTO createdTeamBoard  = teamBoardCommandService.createTeamBoard(teamBoardDTO);
        return ResponseDTO.ok(createdTeamBoard);
    }

    @PutMapping("/{teamBoardId}")
    public ResponseDTO<?> updateTeamBoard(@PathVariable Long teamBoardId,
                                          @RequestBody String boardTitle){
        TeamBoardDTO teamBoardDTO = new TeamBoardDTO();
        teamBoardDTO.setBoardTitle(boardTitle);
        teamBoardDTO.setTeamBoardId(teamBoardId);
        TeamBoardDTO updatedTeamBoard = teamBoardCommandService.updateTeamBoard(teamBoardDTO);
        return ResponseDTO.ok(updatedTeamBoard);
    }

    @DeleteMapping("/{teamBoardId}")
    public ResponseDTO<?> deleteTeamBoard(@PathVariable Long teamBoardId){
        TeamBoardDTO teamBoardDTO = new TeamBoardDTO();
        teamBoardDTO.setTeamBoardId(teamBoardId);
        TeamBoardDTO deletedTeamBoard = teamBoardCommandService.deleteTeamBoard(teamBoardDTO);
        return ResponseDTO.ok(deletedTeamBoard);
    }
}
