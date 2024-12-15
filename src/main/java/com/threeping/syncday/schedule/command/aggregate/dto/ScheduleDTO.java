package com.threeping.syncday.schedule.command.aggregate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.schedule.command.aggregate.entity.PublicStatus;
import com.threeping.syncday.schedule.command.aggregate.entity.MeetingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Schema(name = "commandScheduleDTO")
public class ScheduleDTO {

    @JsonProperty("user_id")
    @Schema(description = "유저 고유번호", example = "1")
    Long userId;

    @JsonProperty("schedule_id")
    @Schema(description = "일정 고유번호", example = "1")
    Long scheduleId;

    @JsonProperty("title")
    @Schema(description = "제목", example = "결혼식")
    String title;

    @JsonProperty("content")
    @Schema(description = "내용", example = "용승 쌤의 결혼식에 가보즈아!")
    String content;

    @JsonProperty("start_time")
    @Schema(description = "시작날짜 및 시간", example = "2024-11-30T13:50:00.000Z")
    Timestamp startTime;

    @JsonProperty("end_time")
    @Schema(description = "종료날짜 및 시간", example = "2024-11-30T14:50:00.000Z")
    Timestamp endTime;

    @JsonProperty("update_time")
    @Schema(description = "일정 생성시간 및 수정시간", example = "2024-11-11T19:30:00.000Z")
    Timestamp updateTime;

    @JsonProperty("public_status")
    @Schema(description = "공개 여부", example = "PUBLIC")
    PublicStatus publicStatus;

    @JsonProperty("meeting_status")
    @Schema(description = "회의 여부", example = "ACTIVE")
    MeetingStatus meetingStatus;

    @JsonProperty("meetingroom_id")
    @Schema(description = "회의실 고유번호", example = "1")
    Long meetingroomId;

    @JsonProperty("attendee_ids")
    @Schema(description = "참석자", example = "[2, 3, 4]")
    List<Long> attendeeIds;

    @JsonProperty("notification_time")
    @Schema(description = "알람 시각", example = "2024-11-30 12:50:00")
    Timestamp notificationTime;
}
