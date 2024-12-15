package com.threeping.syncday.schedule.query.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@Data
@Getter
@Setter
public class AttendeeDTO {

    @JsonProperty("user_id")
    @Schema(description = "참석자", example = "[2, 3, 4]")
    Long userId;
}
