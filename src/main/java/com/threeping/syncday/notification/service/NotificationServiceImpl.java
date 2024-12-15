package com.threeping.syncday.notification.service;

import com.threeping.syncday.notification.infrastructure.InfraNotificationService;
import com.threeping.syncday.notification.redis.RedisNotificationStorage;
import com.threeping.syncday.schedule.query.aggregate.ScheduleDTO;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final RedisNotificationStorage redisNotificationStorage;
    private final InfraNotificationService infraNotificationService;
    private final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    private ScheduledFuture<?> heartbeatTask;

    @Autowired
    public NotificationServiceImpl(RedisNotificationStorage redisNotificationStorage, InfraNotificationService infraNotificationService) {
        this.redisNotificationStorage = redisNotificationStorage;
        this.infraNotificationService = infraNotificationService;
    }

    @PostConstruct
    public void init() {
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        startHeartbeat();
    }

    @PreDestroy
    public void shutdown() {
        if (heartbeatTask != null) {
            heartbeatTask.cancel(true);
        }
        taskScheduler.shutdown();
    }

    @Override
    public SseEmitter createEmitter(Long userId) {

        SseEmitter emitter = new SseEmitter(0L);
        SseEmitter oldEmitter = emitters.put(userId, emitter);

        if (oldEmitter != null) {
            log.info("Old emitter detected for userId: {}. Scheduling completion with delay.", userId);
            taskScheduler.schedule(() -> {
                if (emitters.get(userId) == oldEmitter) {
                    try {
                        oldEmitter.complete();
                        log.info("Delayed completion of old emitter for userId: {}", userId);
                    } catch (Exception e) {
                        log.warn("Failed to complete old emitter for userId: {}", userId, e);
                    }
                }
            }, Instant.now().plus(Duration.ofMillis(500)));

        }

        // 새로운 Emitter에 대한 콜백 등록
        emitter.onCompletion(() -> {
            log.info("OnCompletion() remove {}'s emitter", userId);
            // 현재 등록된 emitter가 이 emitter와 같은 경우에만 제거
            if (emitters.get(userId) == emitter) {
                emitters.remove(userId);
            }
            log.info("After Completion() emitters: {}", emitters);
        });

        emitter.onTimeout(() -> {
            log.info("OnTimeout() remove {}'s emitter", userId);
            if (emitters.get(userId) == emitter) {
                emitters.remove(userId);
            }
            log.info("After Timeout() emitters: {}", emitters);
        });

        emitter.onError((e) -> {
            log.info("OnError() remove {}'s emitter", userId);
            if (emitters.get(userId) == emitter) {
                emitters.remove(userId);
            }
            log.info("error: {}", e);
        });

        log.info("emitters after creating emitter: {}", emitters);

        return emitter;
    }



    @Override
    public void storeScheduleNotification(Long userId, Long scheduleId, Timestamp notificationTime){

        String redisKey = "schedule_notification:" + userId + ":" + scheduleId;
        String redisValue = userId + ":" + scheduleId;
        long ttlInSeconds = Duration
                .between(LocalDateTime.now(),notificationTime.toLocalDateTime())
                .getSeconds();
        log.info("now:{}, notice time: {}",LocalDateTime.now(),notificationTime.toLocalDateTime());
        if (ttlInSeconds > 0) {
            redisNotificationStorage.storeWithTTL(redisKey,redisValue,ttlInSeconds);
            log.info("Notification scheduled for user: '{}' with TTL of {} seconds", userId, ttlInSeconds);
        } else {
            log.warn("TTL for schedule ID '{}' is already expired or invalid.", scheduleId);
            log.warn("ttl: {}",ttlInSeconds);
        }

    }

    @Override
    public void sendScheduleNotification(Long userId, Long scheduleId) {
        log.info("in sendScheduleNotification emitters:{}",emitters);
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                ScheduleDTO scheduleDTO = infraNotificationService.findScheduleByScheduleId(scheduleId);
                emitter.send(SseEmitter.event().data(scheduleDTO));
                log.info("Notification sent to userId: {} for schedule: {}", userId, scheduleDTO.getTitle());
            } catch (Exception e) {
                log.warn("Failed to send notification to userId: {}", userId, e);
                emitters.remove(userId);
            }
        }
    }

    private void startHeartbeat() {
        heartbeatTask = taskScheduler.scheduleAtFixedRate(() -> {
            log.info("In heartbeat emitters: {}",emitters);
            emitters.forEach((userId, emitter) -> {
                try {
                    emitter.send(SseEmitter.event().comment("heartbeat")); // 빈 이벤트 전송
                    log.info("Heartbeat sent to userId: {}", userId);
                } catch (Exception e) {
                    log.warn("Failed to send heartbeat to userId: {}", userId);
                    emitters.remove(userId);
                }
            });
        }, Duration.ofSeconds(60));
    }


}