package com.threeping.syncday.scheduleparticipant.query.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threeping.syncday.scheduleparticipant.command.aggregate.entity.ParticipationStatus;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAttendanceDTO {
    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("schedule_id")
    Long scheduleId;

    @JsonProperty("participation_status")
    ParticipationStatus participationStatus;

    @JsonProperty("notification_time")
    Timestamp notificationTime;

    @JsonProperty("title")
    String title;

    @JsonProperty("content")
    String content;

    @JsonProperty("start_time")
    Timestamp startTime;

    @JsonProperty("end_time")
    Timestamp endTime;

    @JsonProperty("schedule_owner_name")
    String scheduleOwnerName;

    @JsonProperty("team_name")
    String teamName;
}
