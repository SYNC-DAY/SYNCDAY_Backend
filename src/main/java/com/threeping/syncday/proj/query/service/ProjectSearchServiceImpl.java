package com.threeping.syncday.proj.query.service;

import com.threeping.syncday.proj.infrastructure.elasticsearch.document.ProjectSearchDocument;
import com.threeping.syncday.proj.infrastructure.elasticsearch.repository.ProjSearchRepository;
import com.threeping.syncday.proj.query.aggregate.dto.ProjectSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectSearchServiceImpl implements ProjectSearchService {

    private final ProjSearchRepository projSearchRepository;

    @Autowired
    public ProjectSearchServiceImpl(ProjSearchRepository projSearchRepository) {
        this.projSearchRepository = projSearchRepository;
    }

    @Override
    public List<ProjectSearchResponse> searchProject(String keyword) {
        log.info("프로젝트 검색요청으로 들어온 키워드: {}", keyword);

        List<ProjectSearchResponse> responseList = projSearchRepository.
                searchByKeyword(keyword).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        log.info("검색 요청으로 조회된 프로젝트 항목들: {}", responseList);

        return responseList;
    }

    private ProjectSearchResponse convertToResponse(ProjectSearchDocument doc) {
        return ProjectSearchResponse.builder()
                .projectId(doc.getProjectId())
                .projectName(doc.getProjectName())
                .vcsType(doc.getVcsType())
                .createdAt((doc.getCreatedAt()))
                .build();
    }
}
