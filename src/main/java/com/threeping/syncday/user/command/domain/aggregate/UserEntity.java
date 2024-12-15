package com.threeping.syncday.user.command.domain.aggregate;

import com.threeping.syncday.user.config.UserEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "TBL_USER")
@Entity
@EntityListeners(UserEntityListener.class)
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

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "join_year")
    private Timestamp joinYear;

    @Column(name = "position")
    private String position;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "last_activated_at")
    private Timestamp lastAccessTime;

 }