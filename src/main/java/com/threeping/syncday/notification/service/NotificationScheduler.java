package com.threeping.syncday.notification.service;

import com.threeping.syncday.notification.infrastructure.InfraNotificationService;
import com.threeping.syncday.scheduleparticipant.query.aggregate.ScheduleParticipantDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class NotificationScheduler {

    private final NotificationService notificationService;
    private final InfraNotificationService infraNotificationService;

    public NotificationScheduler(NotificationService notificationService,
                                 InfraNotificationService infraNotificationService){
        this.notificationService = notificationService;
        this.infraNotificationService = infraNotificationService;
    }

    @Scheduled(cron = "0 5/10 * * * ?")
    public void scheduleNotificationJob(){
        log.info("일정 알림 scheduling job 실행 시작");

        List<ScheduleParticipantDTO> schedules = infraNotificationService
                .getParticipantsWithNotificationTimeInNext10Minutes();

        for (ScheduleParticipantDTO scheduleParticipantDTO: schedules){
            notificationService.storeScheduleNotification(
                    scheduleParticipantDTO.getUserId(),
                    scheduleParticipantDTO.getScheduleId(),
                    scheduleParticipantDTO.getNotificationTime());
        }
    }
}
