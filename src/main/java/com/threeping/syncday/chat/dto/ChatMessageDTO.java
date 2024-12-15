package com.threeping.syncday.chat.dto;

import com.threeping.syncday.chat.entity.ChatType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChatMessageDTO {

    private String content;
    private String roomId;
    private Long senderId;
    private String senderName;
    private String userProfileImg;
    private ChatType chatType;
    private Date sentTime;

//    private FileUploadDTO file;
}
