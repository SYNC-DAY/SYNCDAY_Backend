package com.threeping.syncday.meetingroomreservation.query.service;

import com.threeping.syncday.meetingroomreservation.query.aggregate.MeetingroomReservationDTO;

import java.util.List;

public interface MeetingroomReservationService {

    List<MeetingroomReservationDTO> getAllMeetingroomReservations();

    List<MeetingroomReservationDTO> getMeetingroomReservationsByRoomId(Long meetingRoomId);

    List<MeetingroomReservationDTO> getMeetingroomReservationsByPlace(String meetingRoomPlace);

    List<MeetingroomReservationDTO> getMeetingroomReservationById(Long scheduleId);
}
