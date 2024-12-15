package com.threeping.syncday.cardboard.command.aggreate.entity;

import com.threeping.syncday.cardboard.query.config.CardboardEntityListener;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@EntityListeners(CardboardEntityListener.class)
@Table(name = "tbl_cardboard")
public class Cardboard {
    @Id
    @Column(name="cardboard_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardboardId;

    @Column(name="title")
    private String title;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="start_time")
    private Timestamp startTime;

    @Column(name="end_time")
    private Timestamp endTime;

    @Column(name = "progress_status")
    private Byte progressStatus;

    @Enumerated(EnumType.STRING)
    @Column(name="vcs_type")
    private VcsType vcsType;

    @Column(name="vcs_milestone_url")
    private String vcsMilestoneUrl;


    @Column(name="workspace_id")
    private Long workspaceId;

    @PrePersist
    public void prePersist(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
//        this.progressStatus = 0;
    }
}
