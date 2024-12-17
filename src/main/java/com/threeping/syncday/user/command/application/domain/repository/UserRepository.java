package com.threeping.syncday.user.command.application.domain.repository;

import com.threeping.syncday.user.command.aggregate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findUserByEmail(String email);
}
