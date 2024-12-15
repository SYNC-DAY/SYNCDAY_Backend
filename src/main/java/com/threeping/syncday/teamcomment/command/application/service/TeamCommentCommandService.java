package com.threeping.syncday.teamcomment.command.application.service;

import com.threeping.syncday.teamcomment.command.aggregate.dto.TeamCommentDTO;

public interface TeamCommentCommandService {
    TeamCommentDTO createTeamComment(TeamCommentDTO teamCommentDTO);

    TeamCommentDTO updateTeamComment(TeamCommentDTO teamCommentDTO);

    TeamCommentDTO deleteTeamComment(Long teamCommentId);
}
