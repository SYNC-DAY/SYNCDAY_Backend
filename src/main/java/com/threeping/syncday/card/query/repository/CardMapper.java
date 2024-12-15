package com.threeping.syncday.card.query.repository;

import com.threeping.syncday.card.query.aggregate.Card;
import com.threeping.syncday.card.query.aggregate.TodayCardDTO;
import com.threeping.syncday.card.query.dto.CardQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardMapper {

    List<Card> selectAllCards();

    List<Card> selectCardsByCardboardId(Long cardboardId);

    CardQueryDTO findByCardId(Long cardId);

    List<CardQueryDTO> findAllCards();

    List<TodayCardDTO> selectTodayCardsByUserId(Long userId);
}
