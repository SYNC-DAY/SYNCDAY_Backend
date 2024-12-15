package com.threeping.syncday.card.query.service;

import com.threeping.syncday.card.query.dto.CardSearchResponse;

import java.util.List;

public interface CardSearchService {
    List<CardSearchResponse> searchCardByKeyword(String keyword);
}
