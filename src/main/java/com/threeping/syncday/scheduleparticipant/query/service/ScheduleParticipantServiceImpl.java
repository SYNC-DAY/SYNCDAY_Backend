package com.threeping.syncday.scheduleparticipant.query.service;

import com.threeping.syncday.scheduleparticipant.query.aggregate.ScheduleAttendanceDTO;
import com.threeping.syncday.scheduleparticipant.query.aggregate.ScheduleParticipantDTO;
import com.threeping.syncday.scheduleparticipant.query.repository.ScheduleParticipantMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class ScheduleParticipantServiceImpl implements ScheduleParticipantService{

    private final ScheduleParticipantMapper scheduleParticipantMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleParticipantServiceImpl(ScheduleParticipantMapper scheduleParticipantMapper, ModelMapper modelMapper) {
        this.scheduleParticipantMapper = scheduleParticipantMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ScheduleParticipantDTO> getParticipantsWithNotificationTimeInNext10Minutes() {
        Timestamp now = Timestamp.from(Instant.now());
        Timestamp tenMinutesLater = Timestamp.from(Instant.now().plusSeconds(600));

        List<ScheduleParticipantDTO> schedules =
                scheduleParticipantMapper.findParticipantsByNotificationTimeBetween(now, tenMinutesLater);

        return schedules;
    }

    @Override
    public List<ScheduleAttendanceDTO> getMyScheduleAttendance(Long userId) {
        List<ScheduleAttendanceDTO> myScheduleAttendance
                = scheduleParticipantMapper.findMyScheduleAttendance(userId);
        return myScheduleAttendance;
    }
}
