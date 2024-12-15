package com.threeping.syncday.cardboard.query.service;

import com.threeping.syncday.cardboard.infrastructure.elasticsearch.document.CardBoardDocument;
import com.threeping.syncday.cardboard.infrastructure.elasticsearch.repository.CardboardSearchRepository;
import com.threeping.syncday.cardboard.query.dto.CardboardSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CardboardSearchServiceImpl implements CardboardSearchService {

    private final CardboardSearchRepository cardboardSearchRepository;

    @Autowired
    public CardboardSearchServiceImpl(CardboardSearchRepository cardboardSearchRepository) {
        this.cardboardSearchRepository = cardboardSearchRepository;
    }

    @Override
    public List<CardboardSearchResponse> searchByKeyword(String keyword) {
        log.info("카드보드 검색으로 들어온 키워드: {}", keyword);

        List<CardboardSearchResponse> responseList = cardboardSearchRepository
                .searchByKeyword(keyword).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return responseList;
    }

    private CardboardSearchResponse convertToResponse(CardBoardDocument doc) {
        return CardboardSearchResponse
                .builder()
                .cardboardId(doc.getCardboardId())
                .cardboardName(doc.getCardboardName())
                .projectId(doc.getProjectId())
                .workspaceId(doc.getWorkspaceId())
                .workspaceName(doc.getWorkspaceName())
                .vcsType(doc.getVcsType())
                .vcsMilestoneUrl(doc.getVcsMilestoneUrl())
                .createdAt((doc.getCreatedAt()))
                .build();
    }
}
