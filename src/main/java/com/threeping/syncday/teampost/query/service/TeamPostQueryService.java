package com.threeping.syncday.teampost.query.service;

import com.github.pagehelper.PageInfo;
import com.threeping.syncday.teampost.query.aggregate.dto.MainTeamPostDTO;
import com.threeping.syncday.teampost.query.aggregate.dto.TeamPostDTO;

import java.util.List;

public interface TeamPostQueryService {
    PageInfo<TeamPostDTO> findTeamBoardPostPage(long teamBoardId, int page, int pageSize, String searchType, String searchQuery);

    TeamPostDTO findTeamPostDetail(Long teamBoardId, Long teamPostId);

    List<MainTeamPostDTO> findMyTeamPost(Long userId);
}
