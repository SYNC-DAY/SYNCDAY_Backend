package com.threeping.syncday.schedule.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.schedule.query.aggregate.ScheduleDTO;
import com.threeping.syncday.schedule.query.service.ScheduleService;
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
public class ScheduleController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Operation(summary = "본인 일정 조회",
            description = "캘린더에 본인 일정 및 참석하는 일정을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "본인 일정 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "success": true,
                                                        "data": [{
                                                                    "title": "결혼식",
                                                                    "start_time": "2024-11-30T13:50:00.000Z",
                                                                    "end_time": "2024-11-30T14:50:00.000Z"
                                                                  },
                                                                  {
                                                                    "title": "워크숍",
                                                                    "start_time": "2024-12-06T09:00:00.000Z",
                                                                    "end_time": "2024-12-07T18:00:00.000Z"
                                                                  }]
                                                        "error": null
                                                    }
                                                    """
                                    )

                            )
                    )
            }
    )
    @GetMapping("/my")
    public ResponseDTO<?> getMySchedules(@RequestParam Long userId) {
        return ResponseDTO.ok(scheduleService.getMySchedulesByUserId(userId));
    }

    @Operation(summary = "타인 일정 조회",
            description = "캘린더에서 타인 검색 시 공개된 일정을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "타인 일정 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "success": true,
                                                        "data": {
                                                                    "start_time": "2024-11-30T13:50:00.000Z",
                                                                    "end_time": "2024-11-30T14:50:00.000Z"
                                                        },
                                                        "error": null
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/others")
    public ResponseDTO<?> getOthersSchedules(@RequestParam Long searchUserId) {
        return ResponseDTO.ok(scheduleService.getOthersSchedulesBySearchUserId(searchUserId));
    }

    @Operation(summary = "본인 일정 상세 조회",
            description = "본인 일정 중 하나의 일정을 상세 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "본인 일정 상세 조회 성공",
                            content = @Content(schema = @Schema(implementation = ScheduleDTO.class))
                    )
            }
    )
    @GetMapping("/my/{scheduleId}")
    public ResponseDTO<?> getMyDetailSchedule(@RequestParam Long userId,
                                              @PathVariable Long scheduleId) {
        return ResponseDTO.ok(scheduleService.getMyDetailSchedulesByUserIdAndScheduleId(userId, scheduleId));
    }

    @GetMapping("/my/today")
    public ResponseDTO<?> getMyTodaySchedule(@RequestParam Long userId){
        return ResponseDTO.ok(scheduleService.getMyTodaySchedule(userId));
    }
}
