package com.threeping.syncday.schedule.query.repository;

import com.threeping.syncday.schedule.query.aggregate.MyTodayScheduleDTO;
import com.threeping.syncday.schedule.query.aggregate.ScheduleDTO;
import com.threeping.syncday.schedule.query.aggregate.ScheduleDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    List<ScheduleDTO> selectMySchedulesByUserId(Long userId);

    List<ScheduleDTO> selectOthersSchedulesBySearchUserId(Long searchUserId);

    List<ScheduleDetailDTO> selectMyDetailSchedulesByUserIdAndScheduleId(@Param("userId") Long userId,
                                                                         @Param("scheduleId") Long scheduleId);

    ScheduleDTO selectByScheduleId(Long scheduleId);

    List<MyTodayScheduleDTO> selectMyTodaySchedule(Long userId);
}
