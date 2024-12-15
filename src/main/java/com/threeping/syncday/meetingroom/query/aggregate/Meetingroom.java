package com.threeping.syncday.meetingroom.query.aggregate;

import com.threeping.syncday.schedule.query.aggregate.UserInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class Meetingroom {

    Long meetingroomId;
    String meetingroomPlace;
    String meetingroomName;
    Integer meetingroomCapacity;
    List<UserInfoDTO> userInfo;
}
