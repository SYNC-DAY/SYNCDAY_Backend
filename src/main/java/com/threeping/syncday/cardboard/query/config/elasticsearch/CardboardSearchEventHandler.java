package com.threeping.syncday.cardboard.query.config.elasticsearch;

import com.threeping.syncday.cardboard.command.aggreate.dto.CardboardEventDTO;
import com.threeping.syncday.cardboard.infrastructure.elasticsearch.document.CardBoardDocument;
import com.threeping.syncday.cardboard.infrastructure.elasticsearch.repository.CardboardSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CardboardSearchEventHandler {

    private final CardboardSearchRepository cardboardSearchRepository;

    @Autowired
    public CardboardSearchEventHandler(CardboardSearchRepository cardboardSearchRepository) {
        this.cardboardSearchRepository = cardboardSearchRepository;
    }

    @EventListener
    public void HandleProEvent(CardboardEventDTO dto) {
        CardBoardDocument doc = CardBoardDocument
                .builder()
                .cardboardId(dto.getCardboardId())
                .cardboardName(dto.getCardboardName())
                .workspaceId(dto.getWorkspaceId())
                .workspaceName(dto.getWorkspaceName())
                .vcsType(dto.getVcsType())
                .vcsMilestoneUrl(dto.getVcsMilestoneUrl())
                .createdAt(dto.getCreatedAt())
                .build();

        switch (dto.getEventType()) {
            case "CREATE" -> cardboardSearchRepository.save(doc);
            case "DELETE" -> cardboardSearchRepository.delete(doc);
            default -> throw new IllegalArgumentException("Unknown event type: " + dto.getEventType());
        }
    }
}
