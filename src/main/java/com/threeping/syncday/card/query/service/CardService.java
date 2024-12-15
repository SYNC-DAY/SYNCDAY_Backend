package com.threeping.syncday.card.query.service;


import com.threeping.syncday.card.query.aggregate.CardDTO;
import com.threeping.syncday.card.query.aggregate.TodayCardDTO;

import java.util.List;

public interface CardService {
    List<CardDTO> getAllCards();

    List<CardDTO> getCardsByCardboardId(Long cardboardId);

    List<TodayCardDTO> getCardsInToday(Long userId);
}
