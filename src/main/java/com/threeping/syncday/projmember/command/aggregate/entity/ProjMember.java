package com.threeping.syncday.projmember.command.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name="TBL_PROJ_MEMBER")
@NoArgsConstructor
@Data
public class ProjMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="proj_member_id")
    private Long projMemberId;

    @Column(name="proj_id")
    private Long projId;

    @Column(name="user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name="bookmark_status")
    private BookmarkStatus bookmarkStatus;

    @Enumerated(EnumType.STRING)
    @Column(name="participation_status")
    private ParticipationStatus participationStatus;


    @PrePersist
    public void prePersist() {
        this.bookmarkStatus = BookmarkStatus.NONE;
    }
}
