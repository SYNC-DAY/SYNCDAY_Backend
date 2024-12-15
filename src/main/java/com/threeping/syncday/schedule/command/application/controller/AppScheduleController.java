package com.threeping.syncday.schedule.command.application.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.schedule.command.Infrastructure.InfraScheduleServiceImpl;
import com.threeping.syncday.schedule.command.aggregate.dto.ScheduleDTO;
import com.threeping.syncday.schedule.command.application.service.AppScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/schedule")
public class  AppScheduleController {
    private final AppScheduleService appScheduleService;

    @Autowired
    public AppScheduleController(AppScheduleService appScheduleService) {
        this.appScheduleService = appScheduleService;
    }

    @Operation(summary = "일정 등록",
            description = "제목, 날짜 및 시간, 내용, 공개 여부 등을 입력 받아 일정 등록을 진행합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일정 등록 성공",
                            content = @Content(schema = @Schema(implementation = ScheduleDTO.class))
                    )
            }
    )
    @PostMapping("")
    public ResponseDTO<?> createSchedule(@RequestBody ScheduleDTO newSchedule) {
        ScheduleDTO createdScheduleDTO = appScheduleService.addSchedule(newSchedule);
        log.info("생성된 스케줄{}",createdScheduleDTO);
        appScheduleService.sendMailToParticipants(createdScheduleDTO);
        return ResponseDTO.ok(createdScheduleDTO);

    }

    @Operation(summary = "일정 수정",
            description = "제목, 날짜 및 시간, 내용, 공개 여부 등 수정한 값을 받아 일정 수정을 진행합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일정 수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "success": true,
                                                        "data": "일정이 수정되었습니다.",
                                                        "error": null
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{scheduleId}")
    public ResponseDTO<?> updateSchedule(@RequestBody ScheduleDTO newSchedule,
                                         @PathVariable Long scheduleId) {
        return ResponseDTO.ok(appScheduleService.modifySchedule(newSchedule, scheduleId));
    }


    @Operation(summary = "일정 삭제",
            description = "등록된 일정을 삭제합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일정 삭제 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "success": true,
                                                        "data": "일정이 삭제되었습니다.",
                                                        "error": null
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{scheduleId}")
    public ResponseDTO<?> deleteSchedule(@PathVariable Long scheduleId) {
        return ResponseDTO.ok(appScheduleService.deleteSchedule(scheduleId));
    }
}
