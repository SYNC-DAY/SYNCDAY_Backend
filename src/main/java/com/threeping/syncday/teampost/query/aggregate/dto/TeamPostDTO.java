package com.threeping.syncday.teampost.query.aggregate.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamPostDTO {
    private Long teamPostId;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long userId;
    private Long comments;
    private String userName;
    private String userPosition;
    private String userProfilePhoto;
    private Long teamBoardId;
}
