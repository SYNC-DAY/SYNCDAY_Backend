package com.threeping.syncday.teamboard.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.teamboard.command.aggregate.dto.TeamBoardDTO;
import com.threeping.syncday.teamboard.query.service.TeamBoardQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teamboard")
public class TeamBoardQueryController {
    private final TeamBoardQueryService teamBoardQueryService;

    public TeamBoardQueryController(TeamBoardQueryService teamBoardQueryService){
        this.teamBoardQueryService = teamBoardQueryService;
    }

    @GetMapping("/{userId}/my")
    public ResponseDTO<?> findMyTeamBoard(@PathVariable Long userId){
        List<TeamBoardDTO> teamBoardDTOs = teamBoardQueryService.findMyTeamBoard(userId);
        return ResponseDTO.ok(teamBoardDTOs);
    }
}
