package com.threeping.syncday.schedule.query.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter
@Setter
public class UserInfoDTO {

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("username")
    String username;

    @JsonProperty("participation_status")
    String participationStatus;

    @JsonProperty("notification_time")
    String notificationTime;
}
