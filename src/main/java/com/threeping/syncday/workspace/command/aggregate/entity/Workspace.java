package com.threeping.syncday.workspace.command.aggregate.entity;

import com.threeping.syncday.workspace.query.config.WorkspaceEntityListener;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@EntityListeners(WorkspaceEntityListener.class)
@Table(name="TBL_WORKSPACE")
@Data
public class Workspace {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="workspace_id")
    private Long workspaceId;

    @Column(name="workspace_name")
    private String workspaceName;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="progress_status")
    private Byte progressStatus;

    @Column(name="proj_id")
    private Long projId;

    @Enumerated(EnumType.STRING)
    @Column(name="vcs_type")
    private VcsType vcsType;

    @Column(name = "vcs_repo_name")
    private String vcsRepoName;

    @Column(name="vcs_repo_url")
    private String vcsRepoUrl;

    /* 설명. 유저가 작성하지 않아도 알아서 세팅해줌 */
    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.progressStatus=0;
    }
}
