package com.threeping.syncday.chat.repository;

import com.threeping.syncday.chat.entity.ChatMessage;
import com.threeping.syncday.chat.entity.ChatType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    Optional<ChatMessage> findTopByRoomIdOrderBySentTimeDesc
            (String roomId);
    
    List<ChatMessage> findByRoomIdOrderBySentTimeAsc(String roomId);

}
