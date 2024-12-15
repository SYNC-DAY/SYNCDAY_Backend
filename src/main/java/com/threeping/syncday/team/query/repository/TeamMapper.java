package com.threeping.syncday.team.query.repository;
import com.threeping.syncday.team.query.aggregate.MyTeamDTO;
import com.threeping.syncday.team.query.aggregate.Team;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamMapper {
    List<Team> selectAllTeams();

    MyTeamDTO selectMyTeam(Long userId);
}
