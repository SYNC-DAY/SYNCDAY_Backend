 package com.threeping.syncday.cardboard.infrastructure.elasticsearch;

import com.threeping.syncday.cardboard.infrastructure.elasticsearch.document.CardBoardDocument;
import com.threeping.syncday.cardboard.infrastructure.elasticsearch.repository.CardboardSearchRepository;
import com.threeping.syncday.cardboard.query.dto.CardboardQueryDTO;
import com.threeping.syncday.cardboard.query.repository.CardboardMapper;
import com.threeping.syncday.proj.infrastructure.elasticsearch.document.ProjectSearchDocument;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component("cardboard_init")
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchInitializer {
    private final CardboardMapper cardboardMapper;
    private final CardboardSearchRepository searchRepository;

    @PostConstruct
    public void init() {
        log.info("카드보드 ES 동기화 시작!");
        synchronizeAll();
    }

    // 수동 동기화 메서드 추가
    public void synchronizeAll() {
        searchRepository.deleteAll();
//        log.info("ES db와 동기화를 위해 es db 모두 비우기");

        List<CardboardQueryDTO> cardboards = cardboardMapper.findAllCardboards();
                cardboards.forEach(dto -> {
                    CardBoardDocument document = CardBoardDocument.builder()
                            .cardboardId(dto.getCardboardId())
                            .cardboardName(dto.getCardboardName())
                            .projectId(dto.getProjectId())
                            .workspaceId(dto.getWorkspaceId())
                            .workspaceName(dto.getWorkspaceName())
                            .vcsType(dto.getVcsType())
                            .vcsMilestoneUrl(dto.getVcsMilestoneUrl())
                            .createdAt(convertToLocalDateTime(dto.getCreatedAt()))
                            .build();
                    searchRepository.save(document);
                });
    }

    private static LocalDateTime convertToLocalDateTime(Timestamp createdAt) {
        if (createdAt == null) {
            return null;
        }
        return createdAt.toLocalDateTime();
    }
}