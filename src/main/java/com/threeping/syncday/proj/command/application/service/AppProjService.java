package com.threeping.syncday.proj.command.application.service;


import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjRequest;
import com.threeping.syncday.proj.command.aggregate.dto.ProjDTO;

public interface AppProjService {
    ProjDTO addProj(UpdateProjRequest req);

    ProjDTO updateProj(UpdateProjRequest req);

    Boolean deleteProj(java.lang.Long projId);
}
