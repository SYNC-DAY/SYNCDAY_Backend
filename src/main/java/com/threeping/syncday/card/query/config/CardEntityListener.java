package com.threeping.syncday.card.query.config;

import com.threeping.syncday.card.command.aggregate.dto.CardEventDTO;
import com.threeping.syncday.card.command.aggregate.entity.Card;
import com.threeping.syncday.card.query.dto.CardQueryDTO;
import com.threeping.syncday.card.query.repository.CardMapper;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CardEntityListener {

    private final ApplicationEventPublisher eventPublisher;
    private final CardMapper cardMapper;

    @Autowired
    public CardEntityListener(ApplicationEventPublisher eventPublisher, CardMapper cardMapper) {
        this.eventPublisher = eventPublisher;
        this.cardMapper = cardMapper;
    }

    @PostPersist
    @PostUpdate
    public void onPostPersistOrUpdate(Card card) {
        CardQueryDTO cardInfo = cardMapper.findByCardId(card.getCardId());

        eventPublisher.publishEvent(CardEventDTO.from(cardInfo));
    }

    @PreRemove
    public void onPreRemove(Card card) {
        CardQueryDTO cardInfo = cardMapper.findByCardId(card.getCardId());

        eventPublisher.publishEvent(CardEventDTO.createDeleted(cardInfo));
    }
}
