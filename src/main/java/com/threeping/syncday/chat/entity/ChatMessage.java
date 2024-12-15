package com.threeping.syncday.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "chat")
@Getter
@Setter
@ToString
public class ChatMessage {

    @Id
    @Field("_id")
    private ObjectId messageId;
    private String content;
    private String roomId;
    private Long senderId;
    private String senderName;
    private ChatType chatType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date sentTime;
}
