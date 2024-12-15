package com.threeping.syncday.teampost.command.application.service;

import com.threeping.syncday.teampost.command.aggregate.dto.TeamPostDTO;

public interface TeamPostCommandService {
    TeamPostDTO createTeamPost(TeamPostDTO teamPostDTO);

    TeamPostDTO updateTeamPost(TeamPostDTO teamPostDTO);

    TeamPostDTO deleteTeamPost(Long teamPostId);
}
