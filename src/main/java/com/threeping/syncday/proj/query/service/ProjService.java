package com.threeping.syncday.proj.query.service;



import com.threeping.syncday.proj.query.aggregate.dto.ProjAndWorkspaceDTO;
import com.threeping.syncday.proj.query.aggregate.dto.ProjDTO;

import java.util.List;

public interface ProjService {
    List<ProjDTO> getAllProjs();

    ProjDTO getProjById(Long projId);

}
