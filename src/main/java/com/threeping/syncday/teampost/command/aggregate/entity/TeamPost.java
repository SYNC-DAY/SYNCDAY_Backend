package com.threeping.syncday.teampost.command.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name="TBL_TEAM_POST")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="team_post_id")
    private Long teamPostId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "team_board_id")
    private Long teamBoardId;
}
