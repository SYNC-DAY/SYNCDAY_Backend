package com.threeping.syncday.team.query.service;

import com.threeping.syncday.team.query.aggregate.MyTeamDTO;
import com.threeping.syncday.team.query.aggregate.Team;
import com.threeping.syncday.team.query.aggregate.TeamDTO;
import com.threeping.syncday.team.query.repository.TeamMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamMapper teamMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(TeamMapper teamMapper, ModelMapper modelMapper) {
        this.teamMapper = teamMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamMapper.selectAllTeams();
        List<TeamDTO> teamDTOs =
                teams.stream().map(team -> modelMapper.map(team, TeamDTO.class)).collect(Collectors.toList());
        return teamDTOs;
    }

    @Override
    public MyTeamDTO getMyTeam(Long userId) {
        return teamMapper.selectMyTeam(userId);
    }
}