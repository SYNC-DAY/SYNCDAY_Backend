package com.threeping.syncday.user.command.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="TBL_USER")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false, length = 512)
    private String password;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "profile_url", length = 2048)
    private String profileUrl;

    @Column(name = "position")
    private String position;

    @JoinColumn(name = "team_id")
    private Long teamId;

    @Column(name = "last_activated_at")
    private LocalDateTime lastActivatedAt;

}
