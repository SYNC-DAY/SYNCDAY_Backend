package com.threeping.syncday.cardboard.command.infrastructure.service;

import com.threeping.syncday.cardboard.command.aggreate.vo.IssueToCardVO;

import java.util.List;

public interface InfraCardboardService {
    Boolean requestAddCards(Long cardboardId, List<IssueToCardVO> cards);
}
