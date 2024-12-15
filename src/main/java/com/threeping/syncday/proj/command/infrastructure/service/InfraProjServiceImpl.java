package com.threeping.syncday.proj.command.infrastructure.service;

import com.threeping.syncday.projmember.command.application.service.AppProjMemberService;
import com.threeping.syncday.projmember.query.service.ProjMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfraProjServiceImpl implements InfraProjService {

    @Override
    public String requestParticipationStatus(Long userId, Long projId) {
        return "";
    }

    @Override
    public Boolean requestAddProjOwner(Long projId, Long userId) {
        return null;
    }
}
