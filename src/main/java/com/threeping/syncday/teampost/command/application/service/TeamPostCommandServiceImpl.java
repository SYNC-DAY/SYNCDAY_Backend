package com.threeping.syncday.teampost.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.teampost.command.aggregate.dto.TeamPostDTO;
import com.threeping.syncday.teampost.command.aggregate.entity.TeamPost;
import com.threeping.syncday.teampost.command.domain.repository.TeamPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TeamPostCommandServiceImpl implements TeamPostCommandService{

    private final TeamPostRepository teamPostRepository;

    @Autowired
    public TeamPostCommandServiceImpl(TeamPostRepository teamPostRepository) {
        this.teamPostRepository = teamPostRepository;
    }

    @Override
    public TeamPostDTO createTeamPost(TeamPostDTO teamPostDTO) {
        TeamPost teamPost = new TeamPost();
        teamPost.setTitle(teamPostDTO.getTitle());
        teamPost.setContent(teamPostDTO.getContent());
        teamPost.setCreatedAt(teamPostDTO.getCreatedAt());
        teamPost.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        teamPost.setUserId(teamPostDTO.getUserId());
        teamPost.setTeamBoardId(teamPostDTO.getTeamBoardId());

        teamPostRepository.save(teamPost);

        teamPostDTO.setTeamPostId(teamPost.getTeamPostId());
        return teamPostDTO;

    }

    @Override
    public TeamPostDTO updateTeamPost(TeamPostDTO teamPostDTO) {
        TeamPost teamPost = teamPostRepository.findById(teamPostDTO.getTeamPostId())
                .orElseThrow(
                        ()->new CommonException(ErrorCode.INVALID_INPUT_VALUE)
                );
        if (!teamPostDTO.getTitle().isEmpty()) {
            teamPost.setTitle(teamPostDTO.getTitle());
        }
        if(!teamPostDTO.getContent().isEmpty()){
            teamPost.setContent(teamPostDTO.getContent());
        }
        teamPost.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        teamPostRepository.save(teamPost);

        teamPostDTO.setUpdatedAt(teamPost.getUpdatedAt());

        return teamPostDTO;
    }

    @Override
    public TeamPostDTO deleteTeamPost(Long teamPostId) {
        TeamPost teamPost = teamPostRepository.findById(teamPostId)
                .orElseThrow(
                        ()->new CommonException(ErrorCode.INVALID_INPUT_VALUE)
                );
        teamPostRepository.delete(teamPost);
        TeamPostDTO deletedTeamPostDTO = new TeamPostDTO();
        deletedTeamPostDTO.setTitle(teamPost.getTitle());
        deletedTeamPostDTO.setContent(teamPost.getContent());
        deletedTeamPostDTO.setTeamPostId(teamPostId);
        deletedTeamPostDTO.setCreatedAt(teamPost.getCreatedAt());
        deletedTeamPostDTO.setUpdatedAt(teamPost.getUpdatedAt());
        deletedTeamPostDTO.setUserId(teamPost.getUserId());
        deletedTeamPostDTO.setTeamBoardId(teamPost.getTeamBoardId());
        return deletedTeamPostDTO;
    }
}
