package com.threeping.syncday.teampost.query.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.teampost.query.aggregate.dto.MainTeamPostDTO;
import com.threeping.syncday.teampost.query.aggregate.dto.TeamPostDTO;
import com.threeping.syncday.teampost.query.repository.TeamPostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeamPostQueryServiceImpl implements TeamPostQueryService{

    private final TeamPostMapper teamPostMapper;

    @Autowired
    public TeamPostQueryServiceImpl(TeamPostMapper teamPostMapper) {
        this.teamPostMapper = teamPostMapper;
    }

    @Override
    public PageInfo<TeamPostDTO> findTeamBoardPostPage(long teamBoardId, int page, int pageSize, String searchType, String searchQuery) {
        PageHelper.startPage(page, pageSize);
        List<TeamPostDTO> teamPostDTOS = null;

        if ((searchType == null || searchType.isEmpty()) && (searchQuery == null || searchQuery.isEmpty())) {
            teamPostDTOS = teamPostMapper.findTeamPostByTeamBoardId(teamBoardId);
            return new PageInfo<>(teamPostDTOS);
        }
        switch (searchType) {
            case "TITLE":
                teamPostDTOS = teamPostMapper.findTeamPostByTeamBoardIdAndTitle(teamBoardId, searchQuery);
                break;
            case "CONTENT":
                teamPostDTOS = teamPostMapper.findTeamPostByTeamBoardIdAndContent(teamBoardId, searchQuery);
                break;
            case "USER":
                teamPostDTOS = teamPostMapper.findTeamPostByTeamBoardIdAndUser(teamBoardId, searchQuery);
                break;
        }

        return new PageInfo<>(teamPostDTOS);
    }



    @Override
    public TeamPostDTO findTeamPostDetail(Long teamBoardId, Long teamPostId) {
        TeamPostDTO teamPostDTO = teamPostMapper.findTeamPostDetailById(teamPostId);
        log.info("teamPostDTO: {}",teamPostDTO);
        if(!teamPostDTO.getTeamBoardId().equals(teamBoardId)){
            throw new CommonException(ErrorCode.INVALID_INPUT_VALUE);
        }
        return teamPostDTO;
    }

    @Override
    public List<MainTeamPostDTO> findMyTeamPost(Long userId) {
        List<MainTeamPostDTO> myTeamPostDTOs = teamPostMapper.findMyTeamPost(userId);
        return myTeamPostDTOs;
    }
}
