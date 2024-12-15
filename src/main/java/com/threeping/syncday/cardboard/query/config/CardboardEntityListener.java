package com.threeping.syncday.cardboard.query.config;

import com.threeping.syncday.cardboard.command.aggreate.dto.CardboardEventDTO;
import com.threeping.syncday.cardboard.command.aggreate.entity.Cardboard;
import com.threeping.syncday.cardboard.query.dto.CardboardQueryDTO;
import com.threeping.syncday.cardboard.query.repository.CardboardMapper;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CardboardEntityListener {

    private final ApplicationEventPublisher eventPublisher;
    private final CardboardMapper cardboardMapper;

    @Autowired
    public CardboardEntityListener(ApplicationEventPublisher eventPublisher, CardboardMapper cardboardMapper) {
        this.eventPublisher = eventPublisher;
        this.cardboardMapper = cardboardMapper;
    }

    @PostPersist
    @PostUpdate
    public void onPostPersistOrUpdate(Cardboard cardboard) {
        CardboardQueryDTO cardboardInfo = cardboardMapper.selectCardboardById(cardboard.getCardboardId());

        eventPublisher.publishEvent(CardboardEventDTO.from(cardboardInfo));
    }

    @PreRemove
    public void onPreRemove(Cardboard cardboard) {
        CardboardQueryDTO cardboardInfo = cardboardMapper.selectCardboardById(cardboard.getCardboardId());

        eventPublisher.publishEvent(CardboardEventDTO.createDeleted(cardboardInfo));
    }
}
