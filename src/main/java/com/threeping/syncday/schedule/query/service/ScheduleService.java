package com.threeping.syncday.schedule.query.service;

import com.threeping.syncday.schedule.query.aggregate.MyTodayScheduleDTO;
import com.threeping.syncday.schedule.query.aggregate.ScheduleDTO;
import com.threeping.syncday.schedule.query.aggregate.ScheduleDetailDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> getMySchedulesByUserId(Long userId);

    List<ScheduleDTO> getOthersSchedulesBySearchUserId(Long searchUserId);

    List<ScheduleDetailDTO> getMyDetailSchedulesByUserIdAndScheduleId(Long userId, Long scheduleId);

    ScheduleDTO getScheduleByScheduleId(Long scheduleId);

    List<MyTodayScheduleDTO> getMyTodaySchedule(Long userId);
}
