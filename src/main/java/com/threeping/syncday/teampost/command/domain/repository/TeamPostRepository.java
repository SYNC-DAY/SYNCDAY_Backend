package com.threeping.syncday.teampost.command.domain.repository;

import com.threeping.syncday.teampost.command.aggregate.entity.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPostRepository extends JpaRepository<TeamPost,Long> {
}
