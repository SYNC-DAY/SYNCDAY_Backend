package com.threeping.syncday.team.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.team.query.aggregate.MyTeamDTO;
import com.threeping.syncday.team.query.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("")
    public ResponseDTO<?> findAllTeams() {
        return ResponseDTO.ok(teamService.getAllTeams());
    }

    @GetMapping("my")
    public ResponseDTO<?> findMyTeam(@RequestParam Long userId){
        MyTeamDTO myTeamDTO = teamService.getMyTeam(userId);
        return ResponseDTO.ok(myTeamDTO);
    }
}
