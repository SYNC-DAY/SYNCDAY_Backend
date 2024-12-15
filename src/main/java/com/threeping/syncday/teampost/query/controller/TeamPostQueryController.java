package com.threeping.syncday.teampost.query.controller;

import com.github.pagehelper.PageInfo;
import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.teampost.query.aggregate.dto.MainTeamPostDTO;
import com.threeping.syncday.teampost.query.aggregate.dto.TeamPostDTO;
import com.threeping.syncday.teampost.query.service.TeamPostQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teampost")
public class TeamPostQueryController {

    private final TeamPostQueryService teamPostQueryService;

    @Autowired
    public TeamPostQueryController(TeamPostQueryService teamPostQueryService) {
        this.teamPostQueryService = teamPostQueryService;
    }

    @GetMapping("/{teamBoardId}")
    public ResponseDTO<?> findTeamBoardPostPage(@PathVariable long teamBoardId,
                                                @RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int pageSize,
                                                @RequestParam(required = false) String searchType,
                                                @RequestParam(required = false) String searchQuery){
        PageInfo<TeamPostDTO> teamPostPageInfo
                = teamPostQueryService.findTeamBoardPostPage(teamBoardId,page,pageSize,searchType,searchQuery);
        return ResponseDTO.ok(teamPostPageInfo);
    }

    @GetMapping("/{teamBoardId}/{teamPostId}")
    public ResponseDTO<?> findTeamPostDetail(@PathVariable Long teamBoardId,
                                             @PathVariable Long teamPostId){
        TeamPostDTO teamPostDTO = teamPostQueryService.findTeamPostDetail(teamBoardId,teamPostId);
        return ResponseDTO.ok(teamPostDTO);
    }

    @GetMapping("/myteam/{userId}")
    public ResponseDTO<?> findMyTeamPost(@PathVariable Long userId){
        List<MainTeamPostDTO> teamPostDTOs = teamPostQueryService.findMyTeamPost(userId);
        return ResponseDTO.ok(teamPostDTOs);
    }

}
