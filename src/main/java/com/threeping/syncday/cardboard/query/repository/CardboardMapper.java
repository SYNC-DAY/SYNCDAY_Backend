package com.threeping.syncday.cardboard.query.repository;

import com.threeping.syncday.cardboard.query.aggregate.Cardboard;
import com.threeping.syncday.cardboard.query.dto.CardboardQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardboardMapper {
    List<Cardboard> selectAllCardBoards();
    List<Cardboard> selectCardBoardsByWorkspaceId(Long workspaceId);
    CardboardQueryDTO selectCardboardById(Long cardboardId);
    List<CardboardQueryDTO> findAllCardboards();
}
