package com.threeping.syncday.user.command.domain.repository;

import com.threeping.syncday.user.command.domain.aggregate.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);

    // 채팅방 멤버 찾기
    List<UserEntity> findByUserIdIn(List<Long> userIds);
    Optional<UserEntity> findByUserId(Long userId);

    @Query("SELECT u.userName FROM UserEntity u WHERE u.userId = :senderId")
    String findUserNameById(@Param("senderId") Long senderId);

    @Override
    List<UserEntity> findAll();
}