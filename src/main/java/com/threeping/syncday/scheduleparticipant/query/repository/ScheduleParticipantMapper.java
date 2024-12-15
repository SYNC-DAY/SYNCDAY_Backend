package com.threeping.syncday.scheduleparticipant.query.repository;

import com.threeping.syncday.scheduleparticipant.query.aggregate.ScheduleAttendanceDTO;
import com.threeping.syncday.scheduleparticipant.query.aggregate.ScheduleParticipantDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ScheduleParticipantMapper {
    List<ScheduleParticipantDTO> findParticipantsByNotificationTimeBetween(
            @Param("start") Timestamp now,
            @Param("end") Timestamp tenMinutesLater);

    List<ScheduleAttendanceDTO> findMyScheduleAttendance(Long userId);
}
