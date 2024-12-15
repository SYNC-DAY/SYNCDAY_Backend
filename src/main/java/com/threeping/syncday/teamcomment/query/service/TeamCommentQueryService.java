package com.threeping.syncday.teamcomment.query.service;

import com.github.pagehelper.PageInfo;
import com.threeping.syncday.teamcomment.query.aggregate.dto.TeamCommentDTO;

public interface TeamCommentQueryService {
    PageInfo<TeamCommentDTO> findTeamCommentPage(Long teamPostId, int page, int pageSize);
}
