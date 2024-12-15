package com.threeping.syncday.cardboard.command.infrastructure.service;

import com.threeping.syncday.card.command.application.service.AppCardService;
import com.threeping.syncday.cardboard.command.aggreate.vo.IssueToCardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfraCardboardServiceImpl implements InfraCardboardService {

    private final AppCardService appCardService;


    @Override
    public Boolean requestAddCards(Long cardboardId, List<IssueToCardVO> cards) {
        appCardService.addCards(cardboardId, cards);
        return Boolean.TRUE;
    }
}
