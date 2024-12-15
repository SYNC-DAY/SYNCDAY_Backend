package com.threeping.syncday.team.query.aggregate;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyTeamDTO {
    private Long userId;
    private String teamName;
    private Long teamId;
}
