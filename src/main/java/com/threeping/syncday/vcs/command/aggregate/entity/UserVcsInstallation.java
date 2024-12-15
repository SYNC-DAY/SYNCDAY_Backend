package com.threeping.syncday.vcs.command.aggregate.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_user_vcs_installation", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_installation", columnNames = {"user_id", "installation_id"})
})
@Where(clause = "deleted_at IS NULL")
public class UserVcsInstallation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "installation_id", nullable = false)
    private Long installationId;

    @Column(name = "scope", columnDefinition = "TEXT")
    private String scope;

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