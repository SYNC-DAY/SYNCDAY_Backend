 package com.threeping.syncday.card.infrastructure.elasticsearch;

import com.threeping.syncday.card.infrastructure.elasticsearch.document.CardDocument;
import com.threeping.syncday.card.infrastructure.elasticsearch.repository.CardSearchRepository;
import com.threeping.syncday.card.query.dto.CardQueryDTO;
import com.threeping.syncday.card.query.repository.CardMapper;
import com.threeping.syncday.cardboard.infrastructure.elasticsearch.document.CardBoardDocument;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

 @Component("card_init")
 @RequiredArgsConstructor
 @Slf4j
 public class ElasticsearchInitializer {
     private final CardMapper cardMapper;
     private final CardSearchRepository searchRepository;

     @PostConstruct
     public void init() {
         log.info("카드 ES 동기화 시작!");
         synchronizeAll();
     }

     // 수동 동기화 메서드 추가
     public void synchronizeAll() {
         searchRepository.deleteAll();
 //        log.info("ES db와 동기화를 위해 es db 모두 비우기");

         List<CardQueryDTO> cards = cardMapper.findAllCards();
                 cards.forEach(dto -> {
//                     log.info("처리중인 카드 정보: {}", dto);
                     CardDocument document = CardDocument.builder()
                             .cardId(dto.getCardId())
                             .cardTitle(dto.getCardTitle())
                             .cardContent(dto.getCardContent())
                             .cardboardId(dto.getCardboardId())
                             .cardboardName(dto.getCardboardName())
                             .workspaceId(dto.getWorkspaceId())
                             .workspaceName(dto.getWorkspaceName())
                             .projectId(dto.getProjectId())
                             .creatorId(dto.getCreatorId())
                             .creatorName(dto.getCreatorName())
                             .assigneeId(dto.getAssigneeId())
                             .assigneeName(dto.getAssigneeName())
                             .tags(dto.getTags())
                             .vcsObject(dto.getVcsObject())
                             .vcsUrl(dto.getVcsUrl())
                             .createdAt(convertToLocalDateTime(dto.getCreatedAt()))
                             .build();
//                     log.info("init으로 저장된 cardDocument 정보: {}", document.toString());
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