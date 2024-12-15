package com.threeping.syncday.projmember.query.service;


import com.threeping.syncday.projmember.query.aggregate.ProjMember;
import com.threeping.syncday.projmember.query.aggregate.ProjMemberDTO;
import com.threeping.syncday.projmember.query.aggregate.dto.UserProjInfoDTO;
import com.threeping.syncday.projmember.query.repository.ProjMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ProjMemberServiceImpl implements ProjMemberService {

    private final ProjMemberMapper projMemberMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjMemberServiceImpl(
            ProjMemberMapper projMemberMapper
            , ModelMapper modelMapper) {
        this.projMemberMapper = projMemberMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProjMemberDTO> getAllProjMembers() {
        List<ProjMember> projMembers = projMemberMapper.selectAllProjMembers();
        List<ProjMemberDTO> projMemberDTOS = projMembers.stream().map(projMember -> modelMapper.map(projMember, ProjMemberDTO.class)).collect(Collectors.toList());
        return projMemberDTOS;
    }

    @Override
    public List<ProjMemberDTO> getProjMembersByProjId(Long projId) {

        List<ProjMember> projMembers = projMemberMapper.selectProjMembersByProjId(projId);
        List<ProjMemberDTO> projMemberDTOS = projMembers.stream().map(projMember -> modelMapper.map(projMember, ProjMemberDTO.class)).collect(Collectors.toList());
        return projMemberDTOS;
    }


    /* 설명. 프로젝트 탭으로 이동시 유저의 아이디를 통해 프로젝트와 워크스페이스 정보 조회 */
    @Override
    public List<UserProjInfoDTO> getProjsByUserId(Long userId) {
        List<UserProjInfoDTO> info = projMemberMapper.selectProjsByUserId(userId);
        info.forEach(projMember -> log.info("projMember: {}", projMember));
        return info;
    }

    @Override
    public String getProjMemberParticipationStatus(Long userId, Long projId) {
        ProjMember projMember = projMemberMapper.selectProjMemberByUserIdAndProjId(userId, projId);
        return projMember.getParticipationStatus();
    }
}
