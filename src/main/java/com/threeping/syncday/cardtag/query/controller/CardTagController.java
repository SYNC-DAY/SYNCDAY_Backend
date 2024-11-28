package com.threeping.syncday.cardtag.query.controller;

import com.threeping.syncday.cardtag.query.service.CardTagService;
import com.threeping.syncday.common.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/card-tags")
public class CardTagController {

    private final CardTagService cardTagService;

    @Autowired
    public CardTagController(CardTagService cardTagService) {
        this.cardTagService = cardTagService;
    }

    @GetMapping("/{tagId}")
    public ResponseDTO<?> findCardTagById(@PathVariable("tagId") Long tagId){
        return ResponseDTO.ok(cardTagService.getCardTagById(tagId));
    }
}
