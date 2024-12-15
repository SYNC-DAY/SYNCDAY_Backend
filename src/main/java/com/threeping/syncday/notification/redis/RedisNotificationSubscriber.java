package com.threeping.syncday.notification.redis;

import com.threeping.syncday.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisNotificationSubscriber implements MessageListener {

    private final NotificationService notificationService;

    @Autowired
    public RedisNotificationSubscriber(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        log.info("Received message: {}", msg);
        String[] parts = msg.split(":");
        Long userId = Long.valueOf(parts[1]);
        Long scheduleId = Long.valueOf(parts[2]);
        log.info("onMessage parsing userId: {}, scheduleId : {}",userId,scheduleId);

        notificationService.sendScheduleNotification(userId, scheduleId);

    }

}
