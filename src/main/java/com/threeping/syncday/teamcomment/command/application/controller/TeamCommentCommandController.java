package com.threeping.syncday.teamcomment.command.application.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.teamcomment.command.aggregate.dto.TeamCommentDTO;
import com.threeping.syncday.teamcomment.command.application.service.TeamCommentCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teamcomment")
public class TeamCommentCommandController {

    private final TeamCommentCommandService teamCommentCommandService;

    @Autowired
    public TeamCommentCommandController(TeamCommentCommandService teamCommentCommandService) {
        this.teamCommentCommandService = teamCommentCommandService;
    }

    @PostMapping("")
    public ResponseDTO<?> createTeamComment(@RequestBody TeamCommentDTO teamCommentDTO){
        TeamCommentDTO createdTeamCommentDTO = teamCommentCommandService.createTeamComment(teamCommentDTO);
        return ResponseDTO.ok(createdTeamCommentDTO);
    }

    @PutMapping("/{teamCommentId}")
    public ResponseDTO<?> updateTeamComment(@PathVariable Long teamCommentId,
                                            @RequestBody TeamCommentDTO teamCommentDTO){
        teamCommentDTO.setTeamCommentId(teamCommentId);
        TeamCommentDTO updatedTeamCommentDTO = teamCommentCommandService.updateTeamComment(teamCommentDTO);
        return ResponseDTO.ok(updatedTeamCommentDTO);
    }

    @DeleteMapping("/{teamCommentId}")
    public ResponseDTO<?> deleteTeamComment(@PathVariable Long teamCommentId){
        TeamCommentDTO deletedTeamCommentDTO = teamCommentCommandService.deleteTeamComment(teamCommentId);
        return ResponseDTO.ok(deletedTeamCommentDTO);
    }
}
