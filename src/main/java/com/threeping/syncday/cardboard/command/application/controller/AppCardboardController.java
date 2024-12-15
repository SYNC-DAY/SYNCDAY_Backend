package com.threeping.syncday.cardboard.command.application.controller;

import com.threeping.syncday.cardboard.command.aggreate.vo.AppCardboardVO;
import com.threeping.syncday.cardboard.command.aggreate.vo.MilestoneToCardboardVO;
import com.threeping.syncday.cardboard.command.application.service.AppCardboardService;
import com.threeping.syncday.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/cardboards")
public class AppCardboardController {

    private final AppCardboardService appCardboardService;

    @Autowired
    public AppCardboardController(AppCardboardService appCardboardService) {
        this.appCardboardService = appCardboardService;
    }

    @PostMapping("/")
    public ResponseDTO<?> createCardboard(@RequestBody AppCardboardVO cardboardVO) {
        return ResponseDTO.ok(appCardboardService.addCardboard(cardboardVO));
    }

    @PutMapping("/")
    public ResponseDTO<?> updateCardboard(@RequestBody AppCardboardVO cardboardVO) {
        return ResponseDTO.ok(appCardboardService.modifyCardboard(cardboardVO));
    }

    @DeleteMapping("/{cardboardId}")
    public ResponseDTO<?> deleteCardboard(@PathVariable("cardboardId") Long cardboardId) {
        return ResponseDTO.ok(appCardboardService.deleteCardboard(cardboardId));
    }

    @PostMapping("/convert")
    public ResponseDTO<?> convertCardboard(@RequestBody MilestoneToCardboardVO vo) {
        log.info("vo: {}", vo);
        return ResponseDTO.ok(appCardboardService.convertMilestoneToCardboard(vo));
    }

}
