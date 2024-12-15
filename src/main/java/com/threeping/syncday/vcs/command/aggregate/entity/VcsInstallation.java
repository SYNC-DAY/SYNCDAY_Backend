package com.threeping.syncday.vcs.command.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
@ToString
@Getter
@Setter
@Entity
@Table(name="tbl_vcs_installation")
@Where(clause = "deleted_at IS NULL")
public class VcsInstallation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "installation_id", nullable = false)
    private String installationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "vcs_type", nullable = false)
    private VcsType vcsType;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "avatar_url", nullable = false, length = 511)
    private String avatarUrl;

    @Column(name = "api_url", nullable = false, length = 511)
    private String apiUrl;

    @Column(name = "html_url", length = 511)
    private String htmlUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InstallationStatus status = InstallationStatus.ACTIVE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

