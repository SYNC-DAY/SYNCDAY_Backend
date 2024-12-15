package com.threeping.syncday.teamboard.command.domain.repository;

import com.threeping.syncday.teamboard.command.aggregate.entity.TeamBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamBoardRepository extends JpaRepository<TeamBoard,Long> {
}
