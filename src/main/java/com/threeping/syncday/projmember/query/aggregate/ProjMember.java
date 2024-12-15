package com.threeping.syncday.projmember.query.aggregate;

import lombok.Data;

@Data
public class ProjMember {
    private Long projMemberId;
    private String bookmarkStatus;
    private String participationStatus;
    private Long userId;
    private Long projId;
    private String userName;
    private String email;
}
