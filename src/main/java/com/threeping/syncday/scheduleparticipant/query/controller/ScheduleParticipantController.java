package com.threeping.syncday.scheduleparticipant.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.scheduleparticipant.query.aggregate.ScheduleAttendanceDTO;
import com.threeping.syncday.scheduleparticipant.query.service.ScheduleParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userschedule")
public class ScheduleParticipantController {

    private final ScheduleParticipantService scheduleParticipantService;

    @Autowired
    public ScheduleParticipantController(ScheduleParticipantService scheduleParticipantService) {
        this.scheduleParticipantService = scheduleParticipantService;
    }

    @GetMapping("/my")
    public ResponseDTO<?> getMyScheduleAttendance(@RequestParam Long userId){
        List<ScheduleAttendanceDTO> scheduleParticipantDTOS
                = scheduleParticipantService.getMyScheduleAttendance(userId);
        return ResponseDTO.ok(scheduleParticipantDTOS);
    }
}
