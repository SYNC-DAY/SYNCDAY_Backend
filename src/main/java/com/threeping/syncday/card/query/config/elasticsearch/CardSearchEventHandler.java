package com.threeping.syncday.card.query.config.elasticsearch;

import com.threeping.syncday.card.command.aggregate.dto.CardEventDTO;
import com.threeping.syncday.card.infrastructure.elasticsearch.document.CardDocument;
import com.threeping.syncday.card.infrastructure.elasticsearch.repository.CardSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CardSearchEventHandler {

    private final CardSearchRepository cardSearchRepository;

    @Autowired
    public CardSearchEventHandler(CardSearchRepository cardSearchRepository) {
        this.cardSearchRepository = cardSearchRepository;
    }

    @EventListener
    public void HandleProEvent(CardEventDTO dto) {
        CardDocument doc = CardDocument
                .builder()
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
                .createdAt(dto.getCreatedAt())
                .build();

        switch (dto.getEventType()) {
            case "CREATE" -> cardSearchRepository.save(doc);
            case "DELETE" -> cardSearchRepository.delete(doc);
            default -> throw new IllegalArgumentException("Unknown event type: " + dto.getEventType());
        }
    }
}
