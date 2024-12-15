package com.threeping.syncday.schedule.query.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.schedule.command.aggregate.entity.MeetingStatus;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyTodayScheduleDTO {
    @JsonProperty("schedule_id")
    Long scheduleId;

    @JsonProperty("title")
    String title;

    @JsonProperty("content")
    String content;

    @JsonProperty("start_time")
    Timestamp startTime;

    @JsonProperty("end_time")
    Timestamp endTime;

    @JsonProperty("update_time")
    Timestamp updateTime;

    @JsonProperty("meeting_status")
    MeetingStatus meetingStatus;

    @JsonProperty("meetingroom_id")
    Long meetingroomId;

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("username")
    String username;

    @JsonProperty("notification_time")
    Timestamp notificationTime;

    @JsonProperty("status")
    String status;
}
