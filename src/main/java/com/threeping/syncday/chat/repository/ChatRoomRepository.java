package com.threeping.syncday.chat.repository;

import com.threeping.syncday.chat.entity.ChatRoom;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    // 유저의 현재 참여 중인 채팅방 목록 조회
    List<ChatRoom> findChatRoomsByMemberIdsContaining(Long userId);
    Optional<ChatRoom> findByRoomId(String roomId);
}
