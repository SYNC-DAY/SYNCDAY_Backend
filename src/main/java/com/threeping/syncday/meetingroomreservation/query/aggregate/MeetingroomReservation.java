package com.threeping.syncday.meetingroomreservation.query.aggregate;

import com.threeping.syncday.schedule.query.aggregate.UserInfoDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MeetingroomReservation {

    Long meetingroomReservationId;
    Timestamp meetingTime;
    Long meetingRoom;
    Long schedule;

    // 추가: title, startTime, endTime 속성 및 getter/setter
     String title;
     Timestamp startTime;
     Timestamp endTime;
     Long user;
     String meetingroomPlace;
     String scheduleParticipant;
     List<UserInfoDTO> userInfo;
}
