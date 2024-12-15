package com.threeping.syncday.teamboard.command.aggregate.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamBoardDTO {
    private Long teamBoardId;
    private Long teamId;
    private String boardTitle;
}
