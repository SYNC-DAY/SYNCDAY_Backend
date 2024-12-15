package com.threeping.syncday.teamcomment.command.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "TBL_TEAM_COMMENT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamComment {

    @Id
    @Column(name = "team_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamCommentId;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "team_post_id")
    private Long teamPostId;

    @Column(name = "user_id")
    private Long userId;

}
