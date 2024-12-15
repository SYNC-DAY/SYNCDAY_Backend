package com.threeping.syncday.vcs.query.repository;

import com.threeping.syncday.vcs.query.aggregate.dto.VcsInstallationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcsInstallationMapper {
    List<VcsInstallationDTO> selectByUserIdAndVcsType(Long userId, String vcsType);
}
