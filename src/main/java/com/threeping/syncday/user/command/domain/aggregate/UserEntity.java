package com.threeping.syncday.user.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "TBL_USER")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_url")
    private String profileUrl;


    @Column(name = "position")
    private String position;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "last_activated_at")
    private Timestamp lastActivatedAt;

 }