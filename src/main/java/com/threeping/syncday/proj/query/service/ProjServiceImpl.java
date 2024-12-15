package com.threeping.syncday.proj.query.service;

import com.threeping.syncday.proj.query.aggregate.dto.ProjAndWorkspaceDTO;
import com.threeping.syncday.proj.query.aggregate.dto.ProjDTO;
import com.threeping.syncday.proj.query.repository.ProjMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjServiceImpl implements ProjService {

    private final ProjMapper projMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjServiceImpl(ProjMapper projMapper, ModelMapper modelMapper) {
        this.projMapper = projMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProjDTO> getAllProjs() {
        return projMapper.selectAllProjs();
    }

    @Override
    public ProjDTO getProjById(Long projId) {

        return projMapper.selectProjById(projId);
    }



}
