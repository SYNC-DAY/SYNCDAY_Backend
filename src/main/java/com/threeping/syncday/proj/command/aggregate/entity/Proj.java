package com.threeping.syncday.proj.command.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Entity
@Table(name="TBL_PROJ")
@Data
public class Proj {
    @Id
    @Column(name="proj_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projId;

    @Column(name="proj_name")
    private String projName;

    @Column(name="start_time")
    private Timestamp startTime;

    @Column(name="end_time")
    private Timestamp endTime;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="progress_status")
    private Byte progressStatus;

    @Enumerated(EnumType.STRING)
    @Column(name="vcs_type")
    private VcsType vcsType;

    @Column(name="vcs_proj_url")
    private String vcsProjUrl;


    /* 설명. 유저가 입력하지 않아도 알아서 설정 */
    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.progressStatus=0;
    }


}
