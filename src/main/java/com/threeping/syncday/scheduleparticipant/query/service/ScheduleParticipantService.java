package com.threeping.syncday.scheduleparticipant.query.service;

import com.threeping.syncday.scheduleparticipant.query.aggregate.ScheduleAttendanceDTO;
import com.threeping.syncday.scheduleparticipant.query.aggregate.ScheduleParticipantDTO;

import java.util.List;

public interface ScheduleParticipantService {
    List<ScheduleParticipantDTO> getParticipantsWithNotificationTimeInNext10Minutes();

    List<ScheduleAttendanceDTO> getMyScheduleAttendance(Long userId);
}
