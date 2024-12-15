package com.threeping.syncday.teamcomment.command.domain.repository;

import com.threeping.syncday.teamcomment.command.aggregate.entity.TeamComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamCommentRepository extends JpaRepository<TeamComment,Long> {
}
