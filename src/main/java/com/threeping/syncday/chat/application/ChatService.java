package com.threeping.syncday.chat.application;

import com.threeping.syncday.chat.dto.ChatMessageDTO;
import com.threeping.syncday.chat.dto.ChatRoomDTO;
import com.threeping.syncday.chat.entity.ChatMessage;
import com.threeping.syncday.chat.entity.ChatRoom;
import com.threeping.syncday.chat.entity.ChatType;
import com.threeping.syncday.chat.repository.ChatMessageRepository;
import com.threeping.syncday.chat.repository.ChatRoomRepository;
import com.threeping.syncday.user.command.domain.aggregate.UserEntity;
import com.threeping.syncday.user.command.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    public ChatService(ChatRoomRepository chatRoomRepository,
                       ChatMessageRepository chatMessageRepository,
                       UserRepository userRepository,
                       SimpMessagingTemplate messagingTemplate) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;

    }

    //  채팅방 목록 조회
    public List<ChatRoomDTO> findUserChat(Long userId) {
        log.info("유저{} 채팅 목록 찾는 서비스 메서드 시작", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("없는 유저입니다: {}", userId);
                    return new IllegalArgumentException("유저를 찾을 수 없습니다.: " + userId);
                });

        log.debug("해당 유저: {}", userId);

        List<ChatRoom> chatRoomList = chatRoomRepository.findChatRoomsByMemberIdsContaining(userId);
        log.info("채팅방 리스트 엔티티: {}", chatRoomList);

        List<ChatRoomDTO> chatRooms = chatRoomList.stream().map(chatRoom -> {
            ChatRoomDTO roomDTO = new ChatRoomDTO();
            roomDTO.setRoomId(chatRoom.getRoomId());
            roomDTO.setChatRoomName(chatRoom.getChatRoomName());

            Optional<ChatMessage> lastMessage = chatMessageRepository.findTopByRoomIdOrderBySentTimeDesc(
                    chatRoom.getRoomId());
            roomDTO.setLastMessage(lastMessage.map(ChatMessage::getContent).orElse("메세지가 없습니다."));
            return roomDTO;
        }).collect(Collectors.toList());
        return chatRooms;
    }

    //  특정 채팅방 조회
    public List<ChatMessageDTO> findChatRoomByRoomId(String roomId) {
        log.info("특정 {} 채팅방 메세지 조회 ", roomId);

        List<ChatMessage> messages = chatMessageRepository.findByRoomIdOrderBySentTimeAsc(roomId);

        return messages.stream()
                .map(this::convertToChatMessage)
                .collect(Collectors.toList());
    }

    //  채팅방 생성(멤버 필터)
    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        log.info("새 채팅방 생성: {}", chatRoom);

        if (chatRoom.getChatRoomName() == null || chatRoom.getChatRoomName().isEmpty()){
            Map<Long,String> userNameMap = getUserNameMap(chatRoom);
            chatRoom.setChatRoomName(getChatRoomName(chatRoom, userNameMap));
            log.info("채팅방 새로 생성");
        }

        if(chatRoom.getLastMessage() == null || chatRoom.getLastMessage().isEmpty()) {
            chatRoom.setLastMessage("새로 채팅방이 만들어졌습니다. 채팅을 시작하세요.");
        }
        return chatRoomRepository.save(chatRoom);
    }

    //  특정 채팅방 퇴장
    public ChatRoom leaveChatRoom(String roomId, Long userId) {
        log.info("유저 {} : {} 채팅방 퇴장", userId, roomId);
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        List<Long> memberIds = new ArrayList<>(chatRoom.getMemberIds());
        memberIds.remove(userId);
        chatRoom.setMemberIds(memberIds);
        log.info("{} 유저 채팅 멤버 목록에서 삭제됨", userId);

        Date leaveTime = new Date();

        ChatMessage leaveRoom = new ChatMessage();
        leaveRoom.setRoomId(roomId);
        leaveRoom.setSenderId(userId);
        leaveRoom.setChatType(ChatType.LEAVE);
        leaveRoom.setSentTime(leaveTime);
        chatMessageRepository.save(leaveRoom);
        log.info("사용자가 채팅방을 나감: roomId={}, userId={}", roomId, userId);

        ChatRoom updatedRoom = chatRoomRepository.save(chatRoom);

        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/leave", updatedRoom);

        return updatedRoom;
    }

    private Map<Long, String> getUserNameMap(ChatRoom chatRoom) {
        return userRepository.findByUserIdIn(chatRoom.getMemberIds())
                .stream()
                .collect(Collectors.toMap(UserEntity::getUserId, UserEntity::getUserName));
    }


// 멤버 초대
    public ChatRoom inviteUser(String roomId, List<Long> userIds ) {
        log.info("{} 채팅방에 새로운 유저 초대: ", roomId, userIds);

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("해당 채팅방이 존재하지 않습니다."));

        List<Long> memberIds = new ArrayList<>(chatRoom.getMemberIds());
        memberIds.addAll(userIds);
        chatRoom.setMemberIds(memberIds);

        ChatRoom updatedRoom = chatRoomRepository.save(chatRoom);

        // 초대된 사용자들의 입장 메시지 생성 및 저장
        for (Long userId : userIds) {
            UserEntity user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            ChatMessage enterMessage = new ChatMessage();
            enterMessage.setRoomId(roomId);
            enterMessage.setSenderId(userId);
            enterMessage.setContent(user.getUserName() + "님이 입장하셨습니다.");
            enterMessage.setChatType(ChatType.ENTER);
            enterMessage.setSentTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
            ChatMessage savedMessage = chatMessageRepository.save(enterMessage);

            // 입장 메시지를 WebSocket을 통해 전송
            ChatMessageDTO chatMessage = convertToChatMessage(savedMessage);
            messagingTemplate.convertAndSend("/topic/messages/" + roomId, chatMessage);
        }
        // 채팅방 정보 업데이트를 모든 참여자에게 브로드캐스트
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/update", updatedRoom);

        // 새로 초대된 사용자들에게 채팅방 목록 업데이트 알림
        userIds.forEach(userId -> {
            messagingTemplate.convertAndSend("/topic/user/" + userId + "/rooms/update", findUserChat(userId));
        });

        return updatedRoom;
    }


//    // 채팅방 이름 수정(단체톡방 일 시)
//    public ChatRoom updateRoomName(String roomId, String newRoom) {
//        log.info("{}채팅방 이름 수정: {}", roomId, newRoom);
//        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
//                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
//
//        chatRoom.setChatRoomName(newRoom);
//        ChatRoom updatedChatRoom = chatRoomRepository.save(chatRoom);
//
//        return ChatRoom(updatedChatRoom, getUserNameMaps(updatedChatRoom));
//    }

    private Map<Long, String> getUserNameMaps(List<ChatRoom> chatRoom) {
        List<Long> allMemberIds = chatRoom.stream()
                .flatMap(room -> room.getMemberIds().stream())
                .distinct()
                .collect(Collectors.toList());

        return userRepository.findByUserIdIn(allMemberIds)
                .stream()
                .collect(Collectors.toMap(UserEntity::getUserId, UserEntity::getUserName));
    }

    //  채팅방 채팅 작성
    public void createMessage(String roomId, ChatMessageDTO chatMessage) {
        log.info("{} 채팅방에 메세지 전송: {}", roomId, chatMessage);
        try {
            ChatMessage message = new ChatMessage();
            message.setRoomId(roomId);
            message.setSenderId(chatMessage.getSenderId());
            message.setSenderName(chatMessage.getSenderName());
            message.setContent(chatMessage.getContent());
            message.setSentTime(new Date());
            message.setChatType(ChatType.valueOf(chatMessage.getChatType().name()));
            chatMessageRepository.save(message);

            messagingTemplate.convertAndSend("/topic/room/" + roomId, chatMessage);
        } catch (Exception e) {
            log.error("메세지 전송 중 오류 발생: {}", e.getMessage(), e);
        }
    }


    // 메세지 db에 저장
// ChatMessageDTO로 변환하여 메세지 db에 저장
    private ChatMessageDTO convertToChatMessage(ChatMessage message) {

        ChatMessageDTO chatMessage = new ChatMessageDTO();
        chatMessage.setRoomId(message.getRoomId());
        chatMessage.setContent(message.getContent());
        chatMessage.setSenderId(message.getSenderId());
        chatMessage.setSenderName(message.getSenderName());
        chatMessage.setChatType(ChatType.valueOf(message.getChatType().name()));
        chatMessage.setSentTime(message.getSentTime());
        return chatMessage;
    }

    public String getChatRoomName(ChatRoom chatRoom, Map<Long, String> userNameMap) {
        List<String> memberNames = chatRoom.getMemberIds().stream()
                .limit(2)
                .map(userNameMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        String nameString = String.join(", ", memberNames);
        return chatRoom.getMemberIds().size() > 2
                ? nameString + " 외 " + (chatRoom.getMemberIds().size() - 2) + "명"
                : nameString;
    }

    public int getMembersCount(ChatRoom chatRoom) {
        return chatRoom.getMemberIds().size();
    }


//  채팅방&채팅 내용 검색

//  첨부 파일 업로드

}

