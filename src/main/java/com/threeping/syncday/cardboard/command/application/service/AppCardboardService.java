package com.threeping.syncday.cardboard.command.application.service;

import com.threeping.syncday.cardboard.command.aggreate.dto.CardboardDTO;
import com.threeping.syncday.cardboard.command.aggreate.vo.AppCardboardVO;
import com.threeping.syncday.cardboard.command.aggreate.vo.MilestoneToCardboardVO;

public interface AppCardboardService {
    CardboardDTO addCardboard(AppCardboardVO cardboardVO);

    CardboardDTO modifyCardboard(AppCardboardVO cardboardVO);

    CardboardDTO deleteCardboard(Long cardboardId);

    Long convertMilestoneToCardboard(MilestoneToCardboardVO vo);
}
