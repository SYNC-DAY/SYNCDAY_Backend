package com.threeping.syncday.proj.command.domain.service;

import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjResponse;

public interface DomainProjService {
    public Boolean isValidMofificationRequest(UpdateProjResponse modifyUpdateProjResponse);
}
