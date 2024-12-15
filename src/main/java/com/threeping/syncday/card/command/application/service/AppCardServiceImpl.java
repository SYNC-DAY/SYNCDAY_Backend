package com.threeping.syncday.card.command.application.service;

import com.threeping.syncday.card.command.aggregate.dto.CardDTO;
import com.threeping.syncday.card.command.aggregate.entity.Card;
import com.threeping.syncday.card.command.aggregate.vo.RequestDeleteCardVO;
import com.threeping.syncday.card.command.aggregate.vo.RequestUpdateCardVO;
import com.threeping.syncday.card.command.domain.repository.CardRepository;
import com.threeping.syncday.cardboard.command.aggreate.vo.IssueToCardVO;
import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppCardServiceImpl implements AppCardService {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AppCardServiceImpl(CardRepository cardRepository, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CardDTO addCard(RequestUpdateCardVO newCard) {
        Card cardToAdd = modelMapper.map(newCard, Card.class);
        log.info("cardToAdd: {}", cardToAdd);
        cardToAdd.setCreatedBy(newCard.getUserId());
        Card addedCard = cardRepository.save(cardToAdd);
        log.info("addedCard: {}", addedCard);
        CardDTO result = modelMapper.map(addedCard, CardDTO.class);
        log.info("result: {}", result);
        return result;
    }

    @Override
    public CardDTO modifyCard(RequestUpdateCardVO updateCard) {
        Card foundCard = cardRepository.findById(updateCard.getCardId()).orElse(null);
        if (foundCard == null) {
            throw new CommonException(ErrorCode.CARD_NOT_FOUND);
        } else if (!Objects.equals(foundCard.getCreatedBy(), updateCard.getUserId())){
            throw new CommonException(ErrorCode.CARD_UNAUTHORIZED_REQUEST);
        }


        /* todo: 요청자와 실제 작성자 비교 도메인 유효성 검증 */
        Card newCard = modelMapper.map(updateCard, Card.class);
        newCard.setCardId(foundCard.getCardId());
        Card addedCard = cardRepository.save(newCard);
        log.info("newCard: {}", newCard);
        log.info("addedCard: {}", addedCard);
        return modelMapper.map(addedCard, CardDTO.class);
    }

    @Override
    public Boolean removeCard(RequestDeleteCardVO deleteCard) {
        Card foundCard = cardRepository.findById(deleteCard.getCardCommentId()).orElse(null);
        if (foundCard == null) {
            throw new CommonException(ErrorCode.CARD_NOT_FOUND);
        } else if (!Objects.equals(foundCard.getCreatedBy(), deleteCard.getUserId())){
            throw new CommonException(ErrorCode.CARD_UNAUTHORIZED_REQUEST);
        }
        cardRepository.delete(foundCard);

        return Boolean.TRUE;
    }

    @Override
    public Boolean addCards(Long cardboardId, List<IssueToCardVO> cards) {
        List<Card> cardsToAdd = cards.stream()
                .map(card->{
                    Card cardToAdd = modelMapper.map(card, Card.class);
                    cardToAdd.setCardboardId(cardboardId);
                    return cardToAdd;
                }).toList();
        cardRepository.saveAll(cardsToAdd);
        return Boolean.TRUE;
    }
}
