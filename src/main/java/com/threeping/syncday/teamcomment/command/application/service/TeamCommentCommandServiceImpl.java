package com.threeping.syncday.teamcomment.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.teamcomment.command.aggregate.dto.TeamCommentDTO;
import com.threeping.syncday.teamcomment.command.aggregate.entity.TeamComment;
import com.threeping.syncday.teamcomment.command.domain.repository.TeamCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TeamCommentCommandServiceImpl implements TeamCommentCommandService{

    private final TeamCommentRepository teamCommentRepository;

    @Autowired
    public TeamCommentCommandServiceImpl(TeamCommentRepository teamCommentRepository) {
        this.teamCommentRepository = teamCommentRepository;
    }

    @Override
    public TeamCommentDTO createTeamComment(TeamCommentDTO teamCommentDTO) {
        TeamComment teamComment = new TeamComment();
        teamComment.setContent(teamCommentDTO.getContent());
        teamComment.setUserId(teamCommentDTO.getUserId());
        teamComment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        teamComment.setTeamPostId(teamCommentDTO.getTeamPostId());
        teamComment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        teamCommentRepository.save(teamComment);

        teamCommentDTO.setTeamCommentId(teamComment.getTeamCommentId());
        teamCommentDTO.setCreatedAt(teamComment.getCreatedAt());
        teamCommentDTO.setUpdatedAt(teamComment.getUpdatedAt());
        return teamCommentDTO;
    }

    @Override
    public TeamCommentDTO updateTeamComment(TeamCommentDTO teamCommentDTO) {
        TeamComment teamComment = teamCommentRepository.findById(teamCommentDTO.getTeamCommentId())
                        .orElseThrow(
                                () -> new CommonException(ErrorCode.INVALID_INPUT_VALUE)
                        );

        teamComment.setContent(teamCommentDTO.getContent());
        teamComment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        teamCommentRepository.save(teamComment);

        teamCommentDTO.setCreatedAt(teamComment.getCreatedAt());
        teamCommentDTO.setUpdatedAt(teamComment.getUpdatedAt());
        return teamCommentDTO;
    }

    @Override
    public TeamCommentDTO deleteTeamComment(Long teamCommentId) {
        TeamComment teamComment = teamCommentRepository.findById(teamCommentId)
                .orElseThrow(
                        () -> new CommonException(ErrorCode.INVALID_INPUT_VALUE)
                );
        TeamCommentDTO deletedTeamCommentDTO = new TeamCommentDTO();
        deletedTeamCommentDTO.setContent(teamComment.getContent());
        deletedTeamCommentDTO.setTeamCommentId(teamComment.getTeamCommentId());
        deletedTeamCommentDTO.setUserId(teamComment.getUserId());
        deletedTeamCommentDTO.setCreatedAt(teamComment.getCreatedAt());
        deletedTeamCommentDTO.setTeamPostId(teamComment.getTeamPostId());
        deletedTeamCommentDTO.setUpdatedAt(teamComment.getUpdatedAt());

        teamCommentRepository.delete(teamComment);

        return deletedTeamCommentDTO;
    }
}
