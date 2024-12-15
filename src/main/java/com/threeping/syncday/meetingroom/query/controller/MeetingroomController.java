package com.threeping.syncday.meetingroom.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.meetingroom.query.service.MeetingroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/meetingroom")
@Slf4j
public class MeetingroomController {

    private MeetingroomService meetingroomService;

    @Autowired
    public MeetingroomController(MeetingroomService meetingroomService) {
        this.meetingroomService = meetingroomService;
    }

    // 모든 회의실 조회
    @GetMapping("")
    public ResponseDTO<?> findAllMeetingrooms() {
        return ResponseDTO.ok(meetingroomService.getAllMeetingrooms());
    }

    // 특정 회의실 조회
    @GetMapping("/{meetingroomId}")
    public ResponseDTO<?> findMeetingroomById(@PathVariable Long meetingroomId) {
        return ResponseDTO.ok(meetingroomService.getMeetingroomById(meetingroomId));
    }
}
