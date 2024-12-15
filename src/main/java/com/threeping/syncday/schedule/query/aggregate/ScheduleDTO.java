package com.threeping.syncday.schedule.query.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.schedule.command.aggregate.entity.MeetingStatus;
import com.threeping.syncday.schedule.command.aggregate.entity.PublicStatus;
import com.threeping.syncday.scheduleparticipant.command.aggregate.entity.ParticipationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Schema(name = "queryScheduleDTO")
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
    @Schema(description = "내용", example = "용승쌤 결혼식을 가자!")
    String content;

    @JsonProperty("start_time")
    @Schema(description = "시작날짜 및 시간", example = "2024-11-30T13:50:00.000Z")
    Timestamp startTime;

    @JsonProperty("end_time")
    @Schema(description = "종료날짜 및 시간", example = "2024-11-30T14:50:00.000Z")
    Timestamp endTime;

    @JsonProperty("public_status")
    @Schema(description = "공개 여부", example = "PUBLIC")
    PublicStatus publicStatus;

    @JsonProperty("meeting_status")
    @Schema(description = "회의 여부", example = "ACTIVE")
    MeetingStatus meetingStatus;

    @JsonProperty("meetingroom_id")
    @Schema(description = "회의실", example = "1")
    Long meetingroomId;

    @JsonProperty("status")
    @Schema(description = "사용자-일정 상태", example = "ATTEND")
    ParticipationStatus status;

    @JsonProperty("notification_time")
    @Schema(description = "알람 시각", example = "2024-11-30 12:50:00")
    Timestamp notificationTime;

    @JsonProperty("attendee_ids")
    @Schema(description = "참석자", example = "[2, 3, 4]")
    List<AttendeeDTO> attendeeIds;

}
