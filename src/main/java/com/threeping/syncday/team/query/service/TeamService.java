package com.threeping.syncday.team.query.service;

import com.threeping.syncday.team.query.aggregate.TeamDTO;

import java.util.List;

public interface TeamService{
    List<TeamDTO> getAllTeams();
}