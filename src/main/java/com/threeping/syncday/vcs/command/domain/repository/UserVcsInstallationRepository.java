package com.threeping.syncday.vcs.command.domain.repository;

import com.threeping.syncday.vcs.command.aggregate.entity.UserVcsInstallation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVcsInstallationRepository extends JpaRepository<UserVcsInstallation, Long> {
    UserVcsInstallation findByUserIdAndInstallationId(Long userId, Long installationId);
}
