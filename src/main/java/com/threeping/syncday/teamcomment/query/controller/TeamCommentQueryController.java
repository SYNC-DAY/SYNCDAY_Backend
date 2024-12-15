package com.threeping.syncday.teamcomment.query.controller;

import com.github.pagehelper.PageInfo;
import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.teamcomment.query.aggregate.dto.TeamCommentDTO;
import com.threeping.syncday.teamcomment.query.service.TeamCommentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teamcomment")
public class TeamCommentQueryController {

    private final TeamCommentQueryService teamCommentQueryService;

    @Autowired
    public TeamCommentQueryController(TeamCommentQueryService teamCommentQueryService) {
        this.teamCommentQueryService = teamCommentQueryService;
    }

    @GetMapping("/{teamPostId}")
    public ResponseDTO<?> findTeamCommentPage(@PathVariable Long teamPostId,
                                              @RequestParam(defaultValue = "1")int page,
                                              @RequestParam(defaultValue = "10") int pageSize){
        PageInfo<TeamCommentDTO> teamCommentPageInfo = teamCommentQueryService.findTeamCommentPage(teamPostId,page,pageSize);
        return ResponseDTO.ok(teamCommentPageInfo);
    }
}

