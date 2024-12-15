package com.threeping.syncday.teampost.command.application.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.teampost.command.aggregate.dto.TeamPostDTO;
import com.threeping.syncday.teampost.command.application.service.TeamPostCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teampost")
public class TeamPostCommandController {

    private final TeamPostCommandService teamPostCommandService;

    @Autowired
    public TeamPostCommandController(TeamPostCommandService teamPostCommandService) {
        this.teamPostCommandService = teamPostCommandService;
    }

    @PostMapping("")
    public ResponseDTO<?> createTeamPost(@RequestBody TeamPostDTO teamPostDTO){
        TeamPostDTO createdTeamPostDTO = teamPostCommandService.createTeamPost(teamPostDTO);
        return ResponseDTO.ok(createdTeamPostDTO);
    }

    @PutMapping("/{teamPostId}")
    public ResponseDTO<?> updateTeamPost(@PathVariable Long teamPostId,
                                         @RequestBody TeamPostDTO teamPostDTO){
        teamPostDTO.setTeamPostId(teamPostId);
        TeamPostDTO updatedTeamPostDTO = teamPostCommandService.updateTeamPost(teamPostDTO);
        return ResponseDTO.ok(updatedTeamPostDTO);
    }

    @DeleteMapping("/{teamPostId}")
    public ResponseDTO<?> deleteTeamPost(@PathVariable Long teamPostId){
        TeamPostDTO deletedTeamPostDTO = teamPostCommandService.deleteTeamPost(teamPostId);
        return ResponseDTO.ok(deletedTeamPostDTO);
    }
}
