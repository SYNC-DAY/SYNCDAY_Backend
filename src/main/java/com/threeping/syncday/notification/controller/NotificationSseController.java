package com.threeping.syncday.notification.controller;

import com.threeping.syncday.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Slf4j
@RestController
@RequestMapping("/sse/notification")
public class NotificationSseController {

    private final NotificationService notificationService;

    public NotificationSseController(NotificationService notificationService){
        this.notificationService = notificationService;

    }

    @GetMapping( value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long userId){
        return notificationService.createEmitter(userId);
    }


}

