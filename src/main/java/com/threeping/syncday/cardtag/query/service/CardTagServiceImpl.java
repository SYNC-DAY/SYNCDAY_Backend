package com.threeping.syncday.cardtag.query.service;

//import com.threeping.syncday.cardtag.query.aggregate.CardTag;
import com.threeping.syncday.cardtag.command.aggregate.entity.CardTag;
import com.threeping.syncday.cardtag.query.aggregate.dto.CardTagDTO;
import com.threeping.syncday.cardtag.query.repository.CardTagMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CardTagServiceImpl implements CardTagService {

    private final CardTagMapper cardTagMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public CardTagServiceImpl(CardTagMapper cardTagMapper, ModelMapper modelMapper) {
        this.cardTagMapper = cardTagMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public CardTagDTO getCardTagById(Long tagId) {
        return cardTagMapper.selectCardTagById(tagId);
    }

//    @Override
//    public CardTagDTO getCardTagByWorkspaceId(Long workspaceId) { return cardTagMapper.selectCardTagsByWorkspaceId(workspaceId); }

    @Override
    public List<CardTagDTO> getTagsByWorkspaceId(Long workspaceId) {
        List<CardTagDTO> tags = cardTagMapper.selectTagsByWorkspaceId(workspaceId);
        if (tags.isEmpty()) {
            throw new NoSuchElementException("No tags found for workspace ID: " + workspaceId);
        }
        return tags.stream()
                .map(tag -> modelMapper.map(tag, CardTagDTO.class))
                .collect(Collectors.toList());
    }
}
