package com.threeping.syncday.projmember.query.service;

import com.threeping.syncday.projmember.query.aggregate.dto.ProjMemberDTO;
import com.threeping.syncday.projmember.query.respository.ProjMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProjMemberServiceImpl implements ProjMemberService {

    private final ProjMemberMapper projMemberMapper;
    @Override
    public List<ProjMemberDTO> getProjMembersByUserId(Long userId) {

        return projMemberMapper.selectProjMembersByUserId(userId);
    }
}
