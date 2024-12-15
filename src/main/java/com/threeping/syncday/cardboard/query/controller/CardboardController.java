package com.threeping.syncday.cardboard.query.controller;
import com.threeping.syncday.cardboard.query.dto.CardboardSearchResponse;
import com.threeping.syncday.cardboard.query.service.CardboardSearchService;
import com.threeping.syncday.cardboard.query.service.CardboardService;
import com.threeping.syncday.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cardboards")
public class CardboardController {

    private final CardboardService cardBoardService;
    private final CardboardSearchService cardboardSearchService;

    public CardboardController(CardboardService cardBoardService, CardboardSearchService cardboardSearchService) {
        this.cardBoardService = cardBoardService;
        this.cardboardSearchService = cardboardSearchService;
    }

    @GetMapping("/")
    public ResponseDTO<?> findAllCardBoards() {
        return ResponseDTO.ok(cardBoardService.getAllCardBoards());
    }

    @GetMapping("/workspaces/{workspaceId}")
    public ResponseDTO<?> findAllCardBoardsByWorkspaceId(@PathVariable Long workspaceId) {
        return ResponseDTO.ok(cardBoardService.getCardBoardsByWorkspaceId(workspaceId));
    }

    @Operation(summary = "카드보드 검색",
            description = "카드보드 이름, VCSTAG를 키워드로 카드보드를 검색합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카드보드 검색 성공",
                            content = @Content(schema = @Schema(implementation = CardboardSearchResponse.class))
                    )
            }
    )
    @GetMapping("/search")
    public ResponseDTO<?> searchCardboard(@RequestParam String keyword) {
        return ResponseDTO.ok(cardboardSearchService.searchByKeyword(keyword));
    }
}
