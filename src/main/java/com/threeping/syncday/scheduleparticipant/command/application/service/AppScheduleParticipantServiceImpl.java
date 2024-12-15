package com.threeping.syncday.scheduleparticipant.command.application.service;

import com.threeping.syncday.scheduleparticipant.command.aggregate.dto.ResponseScheduleParticipantDTO;
import com.threeping.syncday.scheduleparticipant.command.aggregate.dto.ScheduleParticipantNotificationDTO;
import com.threeping.syncday.scheduleparticipant.command.aggregate.dto.ScheduleParticipantStatusDTO;
import com.threeping.syncday.scheduleparticipant.command.aggregate.entity.ParticipationStatus;
import com.threeping.syncday.scheduleparticipant.command.aggregate.entity.ScheduleParticipant;
import com.threeping.syncday.scheduleparticipant.command.domain.repository.ScheduleParticipantRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppScheduleParticipantServiceImpl implements AppScheduleParticipantService{

    private final ScheduleParticipantRepository scheduleParticipantRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AppScheduleParticipantServiceImpl(ScheduleParticipantRepository scheduleParticipantRepository, ModelMapper modelMapper) {
        this.scheduleParticipantRepository = scheduleParticipantRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void addScheduleParticipant(Long userId, Long scheduleId, List<Long> attendeeIds, Timestamp notificationTime) {

        // 참석자 리스트에 있는 attendeeId만 추가
        for (Long attendeeId: attendeeIds) {
            ScheduleParticipant scheduleParticipant = new ScheduleParticipant();

            scheduleParticipant.setUserId(attendeeId);
            scheduleParticipant.setScheduleId(scheduleId);

            scheduleParticipantRepository.save(scheduleParticipant);
        }

        // 주최자도 등록
        ScheduleParticipant scheduleParticipant = new ScheduleParticipant();
        scheduleParticipant.setUserId(userId);
        scheduleParticipant.setScheduleId(scheduleId);
        scheduleParticipant.setParticipationStatus(ParticipationStatus.ATTEND);
        scheduleParticipant.setNotificationTime(notificationTime);      // 처음 설정한 알람 시각

        scheduleParticipantRepository.save(scheduleParticipant);
    }

    @Transactional
    @Override
    public void updateScheduleParticipant(Long userId, Long scheduleId, List<Long> attendeeIds) {

        // 기존 참석자 목록 조회
        List<ScheduleParticipant> currentParticipants = scheduleParticipantRepository.findByScheduleId(scheduleId);

        // 기존 참석자 Id 목록
        Set<Long> currentAttendeeIds = currentParticipants.stream()
                                                        .map(ScheduleParticipant::getUserId)
                                                        .collect(Collectors.toSet());

        Set<Long> newAttendeeIds = new HashSet<>(attendeeIds);

        // 기존에는 있지만 새 목록에는 없는 참석자(삭제해야할 참석자)
        Set<Long> attendeesToRemove = currentAttendeeIds.stream()
                                                    .filter(id -> !newAttendeeIds.contains(id))
                                                    .collect(Collectors.toSet());

        // 기존에는 없지만 새 목록에는 있는 참석자(추가해야할 참석자)
        Set<Long> attendeesToAdd = newAttendeeIds.stream()
                                                .filter(id -> !currentAttendeeIds.contains(id))
                                                .collect(Collectors.toSet());

        // 삭제처리
        for (Long id: attendeesToRemove) {
            scheduleParticipantRepository.deleteByScheduleIdAndUserId(scheduleId, id);
        }

        // 추가처리
        for (Long id: attendeesToAdd) {
            ScheduleParticipant newScheduleParticipant = new ScheduleParticipant();
            newScheduleParticipant.setScheduleId(scheduleId);
            newScheduleParticipant.setUserId(id);

            scheduleParticipantRepository.save(newScheduleParticipant);
        }

        // 주최자도 다시 등록
        ScheduleParticipant scheduleParticipant = new ScheduleParticipant();
        scheduleParticipant.setUserId(userId);
        scheduleParticipant.setScheduleId(scheduleId);
        scheduleParticipant.setParticipationStatus(ParticipationStatus.ATTEND);

        scheduleParticipantRepository.save(scheduleParticipant);
    }

    // 참여 상태 수정
    @Override
    public ResponseScheduleParticipantDTO updateUserScheduleStatus(ScheduleParticipantStatusDTO newScheduleParticipantStatus) {
        log.info("newScheduleParticipantsDTO: {}",newScheduleParticipantStatus);
        ScheduleParticipant currentParticipant = scheduleParticipantRepository.findByScheduleIdAndUserId(
                newScheduleParticipantStatus.getScheduleId(), newScheduleParticipantStatus.getUserId()
        );

        currentParticipant.setParticipationStatus(newScheduleParticipantStatus.getParticipationStatus());

        scheduleParticipantRepository.save(currentParticipant);

        return modelMapper.map(currentParticipant, ResponseScheduleParticipantDTO.class);
    }

    // 알람 시각 수정
    @Override
    public ResponseScheduleParticipantDTO updateUserScheduleNotification(ScheduleParticipantNotificationDTO newScheduleParticipantNotification) {
        ScheduleParticipant currentParticipant = scheduleParticipantRepository.findByScheduleIdAndUserId(
                newScheduleParticipantNotification.getScheduleId(), newScheduleParticipantNotification.getUserId()
        );

        currentParticipant.setNotificationTime(newScheduleParticipantNotification.getNotificationTime());

        scheduleParticipantRepository.save(currentParticipant);

        return modelMapper.map(currentParticipant, ResponseScheduleParticipantDTO.class);
    }
}
