package com.threeping.syncday.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.sql.Timestamp;

public interface NotificationService {
    SseEmitter createEmitter(Long userId);

    void storeScheduleNotification(Long userId, Long scheduleId, Timestamp notificationTime);

    void sendScheduleNotification(Long userId, Long scheduleId);

    }
