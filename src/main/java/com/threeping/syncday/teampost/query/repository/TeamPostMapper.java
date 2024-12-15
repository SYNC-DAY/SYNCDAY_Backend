package com.threeping.syncday.teampost.query.repository;

import com.threeping.syncday.teampost.query.aggregate.dto.MainTeamPostDTO;
import com.threeping.syncday.teampost.query.aggregate.dto.TeamPostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface TeamPostMapper {
    List<TeamPostDTO> findTeamPostByTeamBoardId(@Param("teamBoardId") long teamBoardId);

    TeamPostDTO findTeamPostDetailById(Long teamPostId);

    List<TeamPostDTO> findTeamPostByTeamBoardIdAndTitle(@Param("teamBoardId") long teamBoardId,
                                                        @Param("searchQuery") String searchQuery);

    List<TeamPostDTO> findTeamPostByTeamBoardIdAndContent(@Param("teamBoardId") long teamBoardId,
                                                          @Param("searchQuery") String searchQuery);

    List<TeamPostDTO> findTeamPostByTeamBoardIdAndUser(@Param("teamBoardId") long teamBoardId,
                                                       @Param("searchQuery") String searchQuery);

    List<MainTeamPostDTO> findMyTeamPost(Long userId);
}
