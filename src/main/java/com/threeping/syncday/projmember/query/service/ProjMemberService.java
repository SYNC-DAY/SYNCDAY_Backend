package com.threeping.syncday.projmember.query.service;

import com.threeping.syncday.projmember.query.aggregate.dto.ProjMemberDTO;

import java.util.List;

public interface ProjMemberService {
    List<ProjMemberDTO> getProjMembersByUserId(Long userId);
}
