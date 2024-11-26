package com.threeping.syncday.meetingroomreservation.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.meetingroomreservation.query.service.MeetingroomReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/meetingroom_reservation")
@Slf4j
public class MeetingroomReservationController {

    private MeetingroomReservationService meetingroomReservationService;

    @Autowired
    public MeetingroomReservationController(MeetingroomReservationService meetingroomReservationService) {
        this.meetingroomReservationService = meetingroomReservationService;
    }

    // 모든 회의실 예약 조회
    @GetMapping
    public ResponseDTO<?> findAllMeetingroomReservations() {
        return ResponseDTO.ok(meetingroomReservationService.getAllMeetingroomReservations());
    }

    @GetMapping("/by-room")
    public ResponseDTO<?> findMeetingroomReservationsByRoomId(@RequestParam("meetingroom_id") Long meetingroomId) {
        log.info("Fetching reservations for meetingRoomId: {}", meetingroomId);
        return ResponseDTO.ok(meetingroomReservationService.getMeetingroomReservationsByRoomId(meetingroomId));
    }

    @GetMapping("/by-place")
    public ResponseDTO<?> findMeetingroomReservationsByPlace(@RequestParam("meetingroom_place") String meetingroomPlace) {
        log.info("Fetching reservations for meetingRoomPlace: {}", meetingroomPlace);
        return ResponseDTO.ok(meetingroomReservationService.getMeetingroomReservationsByPlace(meetingroomPlace));
    }
}
