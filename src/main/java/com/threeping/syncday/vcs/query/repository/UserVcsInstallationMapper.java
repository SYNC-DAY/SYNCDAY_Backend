package com.threeping.syncday.vcs.query.repository;

import com.threeping.syncday.vcs.command.aggregate.entity.UserVcsInstallation;
import com.threeping.syncday.vcs.query.aggregate.dto.UserVcsInstallationDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserVcsInstallationMapper {

     UserVcsInstallationDTO selectByUserIdAndInstallationId(Long userId, Long installationId) ;
}
