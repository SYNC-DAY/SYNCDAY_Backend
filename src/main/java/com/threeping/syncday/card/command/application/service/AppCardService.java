package com.threeping.syncday.card.command.application.service;

import com.threeping.syncday.card.command.aggregate.dto.CardDTO;
import com.threeping.syncday.card.command.aggregate.vo.RequestDeleteCardVO;
import com.threeping.syncday.card.command.aggregate.vo.RequestUpdateCardVO;
import com.threeping.syncday.cardboard.command.aggreate.vo.IssueToCardVO;

import java.util.List;

public interface AppCardService {
    CardDTO addCard(RequestUpdateCardVO newCard);

    CardDTO modifyCard(RequestUpdateCardVO updateCard);

    Boolean removeCard(RequestDeleteCardVO deleteCard);

    Boolean addCards(Long cardboardId, List<IssueToCardVO> cards);
}
