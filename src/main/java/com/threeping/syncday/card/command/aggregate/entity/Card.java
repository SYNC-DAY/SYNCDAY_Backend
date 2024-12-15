package com.threeping.syncday.card.command.aggregate.entity;

import com.threeping.syncday.card.query.config.CardEntityListener;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@EntityListeners(CardEntityListener.class)
@Table(name="tbl_card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id")
    private Long cardId;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name="end_time")
    private Timestamp endTime;

    @Enumerated(EnumType.STRING)
    @Column(name="vcs_object_type")
    private VCSOBJECTTYPE vcsObjectType;

    @Column(name="vcs_object_url")
    private String vcsObjectUrl;

    @Column(name="cardboard_id")
    private Long cardboardId;

    @Column(name="tag_id")
    private Long tagId;

    @Column(name="created_by")
    private Long createdBy;

    @Column(name="assignee")
    private Long assignee;
}
