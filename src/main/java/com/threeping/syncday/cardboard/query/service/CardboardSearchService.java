package com.threeping.syncday.cardboard.query.service;

import com.threeping.syncday.cardboard.query.dto.CardboardSearchResponse;

import java.util.List;

public interface CardboardSearchService {
    List<CardboardSearchResponse> searchByKeyword(String keyword);
}
