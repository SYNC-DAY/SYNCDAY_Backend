package com.threeping.syncday.teamcomment.query.repository;

import com.threeping.syncday.teamcomment.query.aggregate.dto.TeamCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamCommentMapper {
    List<TeamCommentDTO> findTeamCommentByTeamPostId(Long teamPostId);
}
