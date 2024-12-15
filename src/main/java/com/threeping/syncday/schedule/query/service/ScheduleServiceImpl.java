package com.threeping.syncday.schedule.query.service;

import com.threeping.syncday.schedule.query.aggregate.MyTodayScheduleDTO;
import com.threeping.syncday.schedule.query.aggregate.ScheduleDTO;
import com.threeping.syncday.schedule.query.aggregate.ScheduleDetailDTO;
import com.threeping.syncday.schedule.query.repository.ScheduleMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleMapper scheduleMapper, ModelMapper modelMapper) {
        this.scheduleMapper = scheduleMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ScheduleDTO> getMySchedulesByUserId(Long userId) {
        List<ScheduleDTO> schedules = scheduleMapper.selectMySchedulesByUserId(userId);
        List<ScheduleDTO> scheduleDTOS =
                schedules.stream()
                        .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                        .collect(Collectors.toList());
        return scheduleDTOS;
    }

    @Override
    public List<ScheduleDTO> getOthersSchedulesBySearchUserId(Long searchUserId) {
        List<ScheduleDTO> schedules = scheduleMapper.selectOthersSchedulesBySearchUserId(searchUserId);
        List<ScheduleDTO> scheduleDTOS =
                schedules.stream()
                        .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                        .collect(Collectors.toList());
        return scheduleDTOS;
    }

    @Override
    public List<ScheduleDetailDTO> getMyDetailSchedulesByUserIdAndScheduleId(Long userId, Long scheduleId) {
        List<ScheduleDetailDTO> schedules = scheduleMapper.selectMyDetailSchedulesByUserIdAndScheduleId(userId, scheduleId);
        List<ScheduleDetailDTO> scheduleDTOS =
                schedules.stream()
                        .map(schedule -> modelMapper.map(schedule, ScheduleDetailDTO.class))
                        .collect(Collectors.toList());
        return scheduleDTOS;
    }

    @Override
    public ScheduleDTO getScheduleByScheduleId(Long scheduleId){
        return scheduleMapper.selectByScheduleId(scheduleId);
    }

    @Override
    public List<MyTodayScheduleDTO> getMyTodaySchedule(Long userId) {
        return scheduleMapper.selectMyTodaySchedule(userId);
    }
}
