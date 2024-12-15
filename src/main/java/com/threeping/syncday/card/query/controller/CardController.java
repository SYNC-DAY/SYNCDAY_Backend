package com.threeping.syncday.card.query.controller;

import com.threeping.syncday.card.query.dto.CardSearchResponse;
import com.threeping.syncday.card.query.service.CardSearchService;
import com.threeping.syncday.card.query.service.CardService;
import com.threeping.syncday.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;
    private final CardSearchService cardSearchService;
    public CardController(CardService cardService, CardSearchService cardSearchService) {
        this.cardService = cardService;
        this.cardSearchService = cardSearchService;
    }

    @GetMapping("/")
    public ResponseDTO<?> findAllCards() {
        return ResponseDTO.ok(cardService.getAllCards());
    }

    @GetMapping("/cardboards/{cardboardId}")
    public ResponseDTO<?> findCardsByCardboardId(@PathVariable("cardboardId") Long cardboardId) {
        return ResponseDTO.ok(cardService.getCardsByCardboardId(cardboardId));
    }

    @Operation(summary = "카드 검색",
            description = "카드 이름, 내용, tag, vcs 객체 키워드로 카드보드를 검색합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카드 검색 성공",
                            content = @Content(schema = @Schema(implementation = CardSearchResponse.class))
                    )
            }
    )
    @GetMapping("/search")
    public ResponseDTO<?> SearchCardsByKeyword(@RequestParam String keyword) {
        return ResponseDTO.ok(cardSearchService.searchCardByKeyword(keyword));
    }

    @GetMapping("/today/{userId}")
    public ResponseDTO<?> findMyCardsInToday(@PathVariable Long userId){
        return ResponseDTO.ok(cardService.getCardsInToday(userId));
    }
}
