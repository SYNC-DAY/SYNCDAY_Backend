package com.threeping.syncday.meetingroomreservation.query.service;

import com.threeping.syncday.meetingroomreservation.query.aggregate.MeetingroomReservationDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class   MeetingroomReservationServiceTests {

    @Autowired
    private MeetingroomReservationService meetingroomReservationService;

    @DisplayName("예약된 회의실 전체 조회")
    @Test
    void testGetAllMeetingroomReservations() {
        // given

        List<MeetingroomReservationDTO> meetingroomReservationList = meetingroomReservationService.getAllMeetingroomReservations();

        // then
        assertNotNull(meetingroomReservationList);
        assertFalse(meetingroomReservationList.isEmpty(), "회의실 예약이 비어있지 않아야 합니다.");
        meetingroomReservationList.forEach(meetingroomReservationDTO -> {
            log.info("meetingroomReservationDTO: {}", meetingroomReservationDTO);
        });
    }
}