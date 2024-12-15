package com.threeping.syncday.meetingroomreservation.command.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeetingroomReservationDTO {

    private Long meetingroomId;
    private Long scheduleId;
    private Long meetingroomReservationId; // 필드 추가
    private Timestamp startTime;
    private Timestamp endTime;
    private String title;
    private String content;
    private Long userId;
    private List<Long> attendeeIds;
}
