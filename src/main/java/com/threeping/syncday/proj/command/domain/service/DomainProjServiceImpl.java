package com.threeping.syncday.proj.command.domain.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjResponse;
import com.threeping.syncday.proj.command.aggregate.entity.Proj;
import com.threeping.syncday.proj.command.domain.repository.ProjRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DomainProjServiceImpl implements DomainProjService {

    private final ProjRepository projRepository;

    @Autowired
    public DomainProjServiceImpl(ProjRepository projRepository) {
        this.projRepository = projRepository;
    }

    @Override
    public Boolean isValidMofificationRequest(UpdateProjResponse modifyUpdateProjResponse) {
        Proj foundProj = projRepository.findByProjId(modifyUpdateProjResponse.projId());
        log.info("foundProj: {}", foundProj);

        // 프로젝트가 없는 경우
        if(foundProj == null) {
            throw new CommonException(ErrorCode.PROJ_NOT_FOUND);
        }

        // 프로젝트 오너가 아닌 경우

        return Boolean.FALSE;
    }
}
