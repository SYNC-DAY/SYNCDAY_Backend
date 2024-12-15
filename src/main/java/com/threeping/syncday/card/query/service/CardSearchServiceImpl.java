package com.threeping.syncday.card.query.service;

import com.threeping.syncday.card.infrastructure.elasticsearch.document.CardDocument;
import com.threeping.syncday.card.infrastructure.elasticsearch.repository.CardSearchRepository;
import com.threeping.syncday.card.query.dto.CardSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CardSearchServiceImpl implements CardSearchService {

    private final CardSearchRepository cardSearchRepository;

    @Autowired
    public CardSearchServiceImpl(CardSearchRepository cardSearchRepository) {
        this.cardSearchRepository = cardSearchRepository;
    }

    @Override
    public List<CardSearchResponse> searchCardByKeyword(String keyword) {
        log.info("카드 검색으로 들어온 키워드: {}", keyword);

        List<CardSearchResponse> responseList = cardSearchRepository
                .findByKeyword(keyword).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return responseList;
    }

    private CardSearchResponse convertToResponse(CardDocument doc) {
        return CardSearchResponse
                .builder()
                .cardId(doc.getCardId())
                .cardTitle(doc.getCardTitle())
                .cardContent(doc.getCardContent())
                .cardboardId(doc.getCardboardId())
                .cardboardName(doc.getCardboardName())
                .workspaceId(doc.getWorkspaceId())
                .workspaceName(doc.getWorkspaceName())
                .projectId(doc.getProjectId())
                .creatorId(doc.getCreatorId())
                .creatorName(doc.getCreatorName())
                .assigneeId(doc.getAssigneeId())
                .assigneeName(doc.getAssigneeName())
                .tags(doc.getTags())
                .vcsObject(doc.getVcsObject())
                .vcsUrl(doc.getVcsUrl())
                .createdAt(doc.getCreatedAt())
                .build();
    }
}
