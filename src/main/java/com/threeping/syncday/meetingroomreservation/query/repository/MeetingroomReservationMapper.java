package com.threeping.syncday.meetingroomreservation.query.repository;

import com.threeping.syncday.meetingroomreservation.query.aggregate.MeetingroomReservation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface MeetingroomReservationMapper {

    List<MeetingroomReservation> selectAllMeetingroomReservations();

    List<MeetingroomReservation> selectMeetingroomReservationsByRoomId(@Param("meetingRoomId") Long meetingRoomId);

    List<MeetingroomReservation> selectMeetingroomReservationsByPlace(@Param("meetingRoomPlace") String meetingRoomPlace);

    List<MeetingroomReservation> selectMeetingroomReservationById(@Param("scheduleId") Long scheduleId);
}
