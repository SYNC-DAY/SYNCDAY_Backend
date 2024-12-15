package com.threeping.syncday.teamboard.command.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_TEAM_BOARD")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamBoard {
    @Id
    @Column(name = "team_board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamBoardId;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "board_title")
    private String boardTitle;
}
