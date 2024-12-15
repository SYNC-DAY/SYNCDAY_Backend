package com.threeping.syncday.schedule.command.Infrastructure;

import com.threeping.syncday.user.command.application.dto.UserDTO;
import org.springframework.scheduling.annotation.Async;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface InfraScheduleService {
    void requestAddScheduleParticipant(Long userId, Long scheduleId, List<Long> attendeeIds, Timestamp notificationTime);

    void requestUpdateScheduleParticipant(Long userId, Long scheduleId, List<Long> attendeeIds);

    UserDTO findUserById(Long userId);

    @Async
    void sendMailToParticipants(List<String> emails,
                                String title,
                                Map<String, Object> varMap);
}
