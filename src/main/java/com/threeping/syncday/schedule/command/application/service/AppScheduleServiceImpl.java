package com.threeping.syncday.schedule.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.schedule.command.Infrastructure.InfraScheduleService;
import com.threeping.syncday.schedule.command.aggregate.dto.ScheduleDTO;
import com.threeping.syncday.schedule.command.aggregate.entity.Schedule;
import com.threeping.syncday.schedule.command.domain.repository.ScheduleRepository;
import com.threeping.syncday.user.command.application.dto.UserDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AppScheduleServiceImpl implements AppScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    private final InfraScheduleService infraScheduleService;

    @Autowired
    public AppScheduleServiceImpl(ScheduleRepository scheduleRepository, ModelMapper modelMapper, InfraScheduleService infraScheduleService) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
        this.infraScheduleService = infraScheduleService;
    }

    @Transactional
    @Override
    public ScheduleDTO addSchedule(ScheduleDTO newScheduleDTO) {

        Schedule newSchedule = new Schedule();
        newSchedule.setTitle(newScheduleDTO.getTitle());
        newSchedule.setContent(newScheduleDTO.getContent());
        newSchedule.setStartTime(newScheduleDTO.getStartTime());
        newSchedule.setEndTime(newScheduleDTO.getEndTime());
        newSchedule.setUpdateTime(Timestamp.from(Instant.now()));
        newSchedule.setPublicStatus(newScheduleDTO.getPublicStatus());
        newSchedule.setMeetingStatus(newScheduleDTO.getMeetingStatus());
        newSchedule.setMeetingroomId(newScheduleDTO.getMeetingroomId());
        newSchedule.setUserId(newScheduleDTO.getUserId());

        scheduleRepository.saveAndFlush(newSchedule);

        // attendeeIds가 null이 아닌 경우에만 infraScheduleService 호출
        if (newScheduleDTO.getAttendeeIds() != null) {
            infraScheduleService.requestAddScheduleParticipant(newSchedule.getUserId()
                    , newSchedule.getScheduleId()
                    , newScheduleDTO.getAttendeeIds()
                    , newScheduleDTO.getNotificationTime());
        }

        ScheduleDTO createdScheduleDTO = modelMapper.map(newSchedule, ScheduleDTO.class);
        createdScheduleDTO.setAttendeeIds(newScheduleDTO.getAttendeeIds());
        return createdScheduleDTO;
    }

    @Transactional
    @Override
    public ScheduleDTO modifySchedule(ScheduleDTO scheduleDTO, Long scheduleId) {

        Schedule newSchedule = scheduleRepository.findByScheduleId(scheduleId);
        if (newSchedule == null) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        newSchedule.setTitle(scheduleDTO.getTitle());
        newSchedule.setContent(scheduleDTO.getContent());
        newSchedule.setStartTime(scheduleDTO.getStartTime());
        newSchedule.setEndTime(scheduleDTO.getEndTime());
        newSchedule.setUpdateTime(Timestamp.from(Instant.now()));
        newSchedule.setPublicStatus(scheduleDTO.getPublicStatus());
        newSchedule.setMeetingStatus(scheduleDTO.getMeetingStatus());
        newSchedule.setMeetingroomId(scheduleDTO.getMeetingroomId());
        newSchedule.setUserId(scheduleDTO.getUserId());

        scheduleRepository.saveAndFlush(newSchedule);

        // attendeeIds가 null이 아닌 경우에만 infraScheduleService 호출
        if (scheduleDTO.getAttendeeIds() != null) {
            infraScheduleService.requestUpdateScheduleParticipant(newSchedule.getUserId()
                    , newSchedule.getScheduleId()
                    , scheduleDTO.getAttendeeIds());
        }

        return modelMapper.map(newSchedule, ScheduleDTO.class);
    }

    @Transactional
    @Override
    public ScheduleDTO deleteSchedule(Long scheduleId) {

        Schedule newSchedule = scheduleRepository.findByScheduleId(scheduleId);
        if (newSchedule == null) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        scheduleRepository.delete(newSchedule);
        
        return modelMapper.map(newSchedule, ScheduleDTO.class);
    }

    @Override
    public void sendMailToParticipants(ScheduleDTO createdScheduleDTO) {
        List<String> emails = createdScheduleDTO.getAttendeeIds().stream().map(
                id->{
                    UserDTO userDTO = infraScheduleService.findUserById(id);
                    return userDTO.getEmail();
                }
        ).toList();
        UserDTO userDTO = infraScheduleService.findUserById(createdScheduleDTO.getUserId());
        String userName = userDTO.getUserName();
        String userPosition = userDTO.getPosition();
        String title = "[SyncDay]" + userName + "(" + userPosition + ")" + " 님의 " + createdScheduleDTO.getTitle() + " 일정 초대 안내 메일";
        Map<String,Object> varMap = new HashMap<>();
        varMap.put("sender",userName + "(" + userPosition + ")");
        varMap.put("title",createdScheduleDTO.getTitle());
        varMap.put("date",createdScheduleDTO.getStartTime() + " ~ " + createdScheduleDTO.getEndTime());
        varMap.put("scheduleId",createdScheduleDTO.getScheduleId());
        infraScheduleService.sendMailToParticipants(emails,title,varMap);
    }
}
