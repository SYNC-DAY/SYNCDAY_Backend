package com.threeping.syncday.vcs.command.domain.repository;

import com.threeping.syncday.vcs.command.aggregate.entity.VcsInstallation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface VcsInstallationRepository extends JpaRepository<VcsInstallation, Long> {
}
