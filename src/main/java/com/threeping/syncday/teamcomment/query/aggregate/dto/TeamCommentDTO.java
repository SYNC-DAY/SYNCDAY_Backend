package com.threeping.syncday.teamcomment.query.aggregate.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamCommentDTO {
    private Long teamCommentId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long teamPostId;
    private Long userId;
    private String userName;
    private String userPosition;
}
