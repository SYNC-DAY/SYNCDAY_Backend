package com.threeping.syncday.meetingroomreservation.query.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.schedule.query.aggregate.UserInfoDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MeetingroomReservationDTO {

   @JsonProperty("meetingroom_reservation_id")
    Long meetingroomReservationId;

   @JsonProperty("meeting_time")
   Timestamp meetingTime;

    @JsonProperty("title")
    String title;

    @JsonProperty("end_time")
    Timestamp endTime;

    @JsonProperty("start_time")
    Timestamp startTime;

   @JsonProperty("meetingroom_id")
    Long meetingRoom;

   @JsonProperty("schedule_id")
    Long schedule;

    @JsonProperty("user_id")
    Long user;

    @JsonProperty("meetingroom_place")
    String meetingroomPlace;

    List<UserInfoDTO> userInfo;
}
