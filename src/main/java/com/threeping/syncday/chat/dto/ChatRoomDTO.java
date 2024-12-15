package com.threeping.syncday.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDTO {
    private String roomId;
    private String chatRoomName;
    private String lastMessage;
}
