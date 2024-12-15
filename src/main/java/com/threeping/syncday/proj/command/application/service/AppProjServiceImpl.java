package com.threeping.syncday.proj.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.proj.command.aggregate.entity.Proj;
import com.threeping.syncday.proj.command.domain.repository.ProjRepository;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjRequest;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjResponse;
import com.threeping.syncday.proj.command.aggregate.dto.ProjDTO;
import com.threeping.syncday.projmember.command.aggregate.vo.RequestUpdateVcsInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppProjServiceImpl implements AppProjService {

    private final ProjRepository projRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProjDTO addProj(UpdateProjRequest req) {
        Proj proj = modelMapper.map(req, Proj.class);
        Proj addedProj = projRepository.save(proj);
        return modelMapper.map(addedProj, ProjDTO.class);
    }


    public ProjDTO updateProj(UpdateProjRequest req) {
        log.info("req: {}", req);

        Proj foundProj = projRepository.findById(req.projId())
                .orElseThrow(() -> new CommonException(ErrorCode.PROJ_NOT_FOUND));
        foundProj.setVcsType(req.vcsType());
        if (req.projName() != null) {
            foundProj.setProjName(req.projName());
        }
        if (req.startTime() != null) {
            foundProj.setStartTime(req.startTime());
        }
        if (req.endTime() != null) {
            foundProj.setEndTime(req.endTime());
        }
        if(req.vcsType() != null) {
            foundProj.setVcsType(req.vcsType());
        }
        if (req.vcsProjUrl() != null) {
            foundProj.setVcsProjUrl(req.vcsProjUrl());
        }
        if (req.githubInstallationId()!=null){
            foundProj.setVcsInstallationId(req.githubInstallationId());
        }

        Proj updatedProj = projRepository.save(foundProj);
        log.info("updatedProj: {}", updatedProj);
        return modelMapper.map(updatedProj, ProjDTO.class);
    }

    @Override
    public Boolean deleteProj(Long projId) {
        Proj proj = projRepository.findById(projId).orElse(null);
        if (proj == null) {
            throw new CommonException(ErrorCode.PROJ_NOT_FOUND);
        }
        projRepository.delete(proj);
        return Boolean.TRUE;
    }


}
