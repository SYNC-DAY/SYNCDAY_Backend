package com.threeping.syncday.card.query.service;

import com.threeping.syncday.card.query.aggregate.Card;
import com.threeping.syncday.card.query.aggregate.CardDTO;
import com.threeping.syncday.card.query.aggregate.TodayCardDTO;
import com.threeping.syncday.card.query.repository.CardMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardMapper cardMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public CardServiceImpl(CardMapper cardMapper, ModelMapper modelMapper) {
        this.cardMapper = cardMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CardDTO> getAllCards() {
        List<Card> cards = cardMapper.selectAllCards();
        List<CardDTO> cardDTOs = cards
                .stream()
                .map(card -> modelMapper.map(card, CardDTO.class))
                .collect(Collectors.toList());
        return cardDTOs;
    }

    @Override
    public List<CardDTO> getCardsByCardboardId(Long cardboardId) {

        List<Card> cards  = cardMapper.selectCardsByCardboardId(cardboardId);

        List<CardDTO> cardDTOs = cards
                .stream()
                .map(card -> modelMapper.map(card, CardDTO.class))
                .collect(Collectors.toList());
        return cardDTOs;
    }

    @Override
    public List<TodayCardDTO> getCardsInToday(Long userId) {

        List<TodayCardDTO> cards = cardMapper.selectTodayCardsByUserId(userId);

        return cards;
    }
}
