package com.threeping.syncday.chat.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "chatroom")
@Getter
@Setter
@ToString
public class ChatRoom {
    @Id
    private String roomId = UUID.randomUUID().toString();
    private List<Long> memberIds;
    private String chatRoomName;
    private String lastMessage;
}
