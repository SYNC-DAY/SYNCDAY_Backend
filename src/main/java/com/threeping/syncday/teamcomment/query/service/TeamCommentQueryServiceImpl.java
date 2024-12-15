package com.threeping.syncday.teamcomment.query.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.threeping.syncday.teamcomment.query.aggregate.dto.TeamCommentDTO;
import com.threeping.syncday.teamcomment.query.repository.TeamCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamCommentQueryServiceImpl implements TeamCommentQueryService{
    private final TeamCommentMapper teamCommentMapper;

    @Autowired
    public TeamCommentQueryServiceImpl(TeamCommentMapper teamCommentMapper) {
        this.teamCommentMapper = teamCommentMapper;
    }

    @Override
    public PageInfo<TeamCommentDTO> findTeamCommentPage(Long teamPostId, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);

        List<TeamCommentDTO> teamCommentDTOS = teamCommentMapper.findTeamCommentByTeamPostId(teamPostId);

        return new PageInfo<>(teamCommentDTOS);
    }
}
