package com.threeping.syncday.cardtag.query.service;

import com.threeping.syncday.cardtag.query.aggregate.dto.CardTagDTO;

import java.util.List;

public interface CardTagService {
    CardTagDTO getCardTagById(Long tagId);

//    CardTagDTO getCardTagByWorkspaceId(Long workspaceId);
//    List<CardTagDTO> getCardTagByWorkspaceId(Long workspaceId);

    List<CardTagDTO> getTagsByWorkspaceId(Long workspaceId);
}
