package com.threeping.syncday.chat.config;

import com.mongodb.client.MongoClient;

import com.threeping.syncday.chat.entity.ChatMessage;
import com.threeping.syncday.chat.entity.ChatRoom;
import com.threeping.syncday.chat.entity.ChatType;
import com.threeping.syncday.chat.repository.ChatMessageRepository;
import com.threeping.syncday.chat.repository.ChatRoomRepository;
import com.threeping.syncday.user.command.domain.aggregate.UserEntity;
import com.threeping.syncday.user.command.domain.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;


@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "chatdb");
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           ChatRoomRepository chatRoomRepository,
                           ChatMessageRepository chatMessageRepository) {
        return args -> {
            // MongoDB 기존 데이터 초기화
            chatRoomRepository.deleteAll();
            chatMessageRepository.deleteAll();

            // 유저 데이터 조회 (RDBMS에서)
            UserEntity user1 = userRepository.findByUserId(1L)
                    .orElseThrow(() -> new RuntimeException("User 1 not found"));
            UserEntity user2 = userRepository.findByUserId(2L)
                    .orElseThrow(() -> new RuntimeException("User 2 not found"));
            UserEntity user3 = userRepository.findByUserId(3L)
                    .orElseThrow(() -> new RuntimeException("User 3 not found"));
            UserEntity user4 = userRepository.findByUserId(4L)
                    .orElseThrow(() -> new RuntimeException("User 4 not found"));
            UserEntity user5 = userRepository.findByUserId(5L)
                    .orElseThrow(() -> new RuntimeException("User 5 not found"));
            UserEntity user6 = userRepository.findByUserId(6L)
                    .orElseThrow(() -> new RuntimeException("User 6 not found"));
            UserEntity user7 = userRepository.findByUserId(7L)
                    .orElseThrow(() -> new RuntimeException("User 7 not found"));
            UserEntity user8 = userRepository.findByUserId(8L)
                    .orElseThrow(() -> new RuntimeException("User 8 not found"));
            UserEntity user9 = userRepository.findByUserId(9L)
                    .orElseThrow(() -> new RuntimeException("User 9 not found"));
            UserEntity user10 = userRepository.findByUserId(10L)
                    .orElseThrow(() -> new RuntimeException("User 10 not found"));
            UserEntity user11 = userRepository.findByUserId(11L)
                    .orElseThrow(() -> new RuntimeException("User 11 not found"));

            // 채팅방 더미 데이터 생성
            ChatRoom room1 = createChatRoom(
                    String.join(" ", user1.getUserName(), user11.getUserName()),
                    Arrays.asList(user1.getUserId(), user11.getUserId())
            );

            ChatRoom room2 = createChatRoom(
                    String.join(" ", user2.getUserName(), user3.getUserName(), user10.getUserName(), user11.getUserName()),
                    Arrays.asList(user2.getUserId(), user3.getUserId(), user10.getUserId(), user11.getUserId())
            );

            ChatRoom room3 = createChatRoom(
                    String.join(" ", user1.getUserName(), user6.getUserName(), user9.getUserName(), user10.getUserName()),
                    Arrays.asList(user1.getUserId(), user6.getUserId(), user9.getUserId(),user10.getUserId()));

            ChatRoom room4 = createChatRoom(
                    String.join(" ", user8.getUserName(), user9.getUserName(), user10.getUserName()),
                    Arrays.asList(user8.getUserId(), user9.getUserId(), user10.getUserId())
            );

            ChatRoom room5 = createChatRoom(
                    String.join(" ", user1.getUserName(), user11.getUserName(), user7.getUserName()),
                    Arrays.asList(user1.getUserId(), user11.getUserId(), user7.getUserId())
            );

            ChatRoom room6 = createChatRoom(
                    String.join(" ", user2.getUserName(), user4.getUserName(), user6.getUserName()),
                    Arrays.asList(user2.getUserId(), user4.getUserId(), user6.getUserId())
            );

            ChatRoom room7 = createChatRoom(
                    String.join(" ", user3.getUserName(), user5.getUserName(), user9.getUserName()),
                    Arrays.asList(user3.getUserId(), user5.getUserId(), user9.getUserId())
            );

            ChatRoom room8 = createChatRoom(
                    String.join(" ", user10.getUserName(), user11.getUserName()),
                    Arrays.asList(user10.getUserId(), user11.getUserId())
            );

            ChatRoom room9 = createChatRoom(
                    String.join(" ", user8.getUserName(), user2.getUserName(), user1.getUserName()),
                    Arrays.asList(user8.getUserId(), user2.getUserId(), user1.getUserId())
            );

            ChatRoom room10 = createChatRoom(
                    String.join(" ", user6.getUserName(), user4.getUserName(), user3.getUserName(), user11.getUserName()),
                    Arrays.asList(user6.getUserId(), user4.getUserId(), user3.getUserId(), user11.getUserId())
            );
            ChatRoom room11 = createChatRoom(
                    String.join(" ", user2.getUserName(), user4.getUserName(), user3.getUserName(), user11.getUserName()),
                    Arrays.asList(user2.getUserId(), user4.getUserId(), user3.getUserId(), user11.getUserId())
            );
            ChatRoom room12 = createChatRoom(
                    String.join(" ", user1.getUserName(), user4.getUserName(), user3.getUserName(), user11.getUserName()),
                    Arrays.asList(user1.getUserId(), user4.getUserId(), user3.getUserId(), user11.getUserId())
            );
            ChatRoom room13 = createChatRoom(
                    String.join(" ", user1.getUserName(), user3.getUserName()),
                    Arrays.asList(user1.getUserId(), user3.getUserId())
            );
            ChatRoom room14 = createChatRoom(
                    String.join(" ", user7.getUserName(), user2.getUserName()),
                    Arrays.asList(user7.getUserId(), user2.getUserId())
            );
            ChatRoom room15 = createChatRoom(
                    String.join(" ", user6.getUserName(), user4.getUserName()),
                    Arrays.asList(user6.getUserId(), user4.getUserId())
            );
            ChatRoom room16 = createChatRoom(
                    String.join(" ", user1.getUserName(), user2.getUserName(), user3.getUserName()),
                    Arrays.asList(user1.getUserId(), user2.getUserId(), user3.getUserId())
            );
            ChatRoom room17 = createChatRoom(
                    String.join(" ", user6.getUserName(), user4.getUserName()),
                    Arrays.asList(user6.getUserId(), user4.getUserId())
            );
            ChatRoom room18 = createChatRoom(
                    String.join(" ", user11.getUserName(),user3.getUserName()),
                    Arrays.asList(user11.getUserId(),user3.getUserId())
            );
            ChatRoom room19 = createChatRoom(
                    String.join(" ", user1.getUserName(), user4.getUserName()),
                    Arrays.asList(user1.getUserId(), user4.getUserId())
            );
            ChatRoom room20 = createChatRoom(
                    String.join(" ", user6.getUserName(), user11.getUserName()),
                    Arrays.asList(user6.getUserId(), user11.getUserId())
            );

            chatRoomRepository.saveAll(Arrays.asList(room1, room2, room3, room4,room5,room6,room7,room8,room9,room10,
                    room11, room12, room13, room14,room15,room16,room17,room18,room19,room20));

            // MongoDB 채팅 메시지 더미 데이터 생성
//      MongoDB 채팅 메시지 더미 데이터 생성
            List<ChatMessage> messages = Arrays.asList(
                    createChatMessage(room1.getRoomId(), user1.getUserId(), user1.getUserName(), "오늘 팀 미팅 준비 다 되셨나요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room1.getRoomId(), user11.getUserId(), user11.getUserName(), "아직 준비 중입니다. 자료 정리 중이에요.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room1.getRoomId(), user1.getUserId(), user1.getUserName(), "알겠습니다. 자료 공유해 주세요!",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),

                    createChatMessage(room2.getRoomId(), user2.getUserId(), user2.getUserName(), "이번 프로젝트에서는 어떤 기술 스택을 사용하시나요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room2.getRoomId(), user3.getUserId(), user3.getUserName(), "우리는 React와 Spring Boot 조합을 사용할 예정입니다.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room2.getRoomId(), user10.getUserId(), user10.getUserName(), "그렇다면 REST API는 어떻게 설계하나요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room2.getRoomId(), user11.getUserId(), user11.getUserName(), "그 부분은 API 설계를 맡고 있습니다. 함께 공유할게요.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),

                    createChatMessage(room3.getRoomId(), user1.getUserId(), user1.getUserName(), "오늘 코드 리뷰 진행할 건가요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room3.getRoomId(), user6.getUserId(), user6.getUserName(), "네, 코드 리뷰는 오후 3시에 시작할 예정입니다.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room3.getRoomId(), user9.getUserId(), user9.getUserName(), "혹시 내가 맡은 부분 코드 리뷰도 해주실 수 있나요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room3.getRoomId(), user10.getUserId(), user10.getUserName(), "물론이죠. 오후 3시에 시간 맞춰 보겠습니다.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 4
                    createChatMessage(room4.getRoomId(), user8.getUserId(), user8.getUserName(), "오늘은 Jira 티켓을 어떻게 처리할지 고민 중입니다.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room4.getRoomId(), user9.getUserId(), user9.getUserName(), "티켓 우선순위를 정해두고 처리하는 게 좋을 것 같아요.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room4.getRoomId(), user10.getUserId(), user10.getUserName(), "그러면 오늘은 급한 것부터 처리하고, 나중에 더 중요한 것들을 나누어서 할까요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 5
                    createChatMessage(room5.getRoomId(), user1.getUserId(), user1.getUserName(), "내일 점심 시간에 회식 장소 정하는데, 의견 있나요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room5.getRoomId(), user11.getUserId(), user11.getUserName(), "저는 고기 먹고 싶어요. 바베큐는 어때요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room5.getRoomId(), user7.getUserId(), user7.getUserName(), "좋아요! 그런데 비건도 고려해야 할 것 같습니다.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 6
                    createChatMessage(room6.getRoomId(), user2.getUserId(), user2.getUserName(), "이번 스프린트에서는 어떤 기능을 먼저 개발해야 할까요?",
                           new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room6.getRoomId(), user4.getUserId(), user4.getUserName(), "우리는 우선, 로그인 기능부터 시작하는 게 좋을 것 같습니다.",
                           new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                           new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 7
                    createChatMessage(room7.getRoomId(), user3.getUserId(), user3.getUserName(), "혹시 오늘 새로운 프론트엔드 디자인 리뷰할 시간 있나요?",
                           new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room7.getRoomId(), user5.getUserId(), user5.getUserName(), "저는 그 시간 괜찮아요. 일단 자료를 공유할게요.",
                           new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room7.getRoomId(), user9.getUserId(), user9.getUserName(), "디자인 리뷰는 항상 기대되네요. 새로운 레이아웃이 궁금합니다.",
                           new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 8
                    createChatMessage(room8.getRoomId(), user10.getUserId(), user10.getUserName(), "이번 분기 실적 보고서 작성은 누가 맡을까요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room8.getRoomId(), user11.getUserId(), user11.getUserName(), "제가 맡을게요. 주요 내용 정리해서 바로 공유할게요.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 9
                    createChatMessage(room9.getRoomId(), user8.getUserId(), user8.getUserName(), "새로운 프로젝트 아이디어가 있는데 공유해도 될까요?",
                           new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room9.getRoomId(), user2.getUserId(), user2.getUserName(), "네, 어떤 아이디어인가요?",
                           new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room9.getRoomId(), user1.getUserId(), user1.getUserName(), "아이디어 좋은 것 같아요. 어떻게 진행할지 논의해봐요.",
                           new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 10
                    createChatMessage(room10.getRoomId(), user6.getUserId(), user6.getUserName(), "새로운 버그 수정한 사항을 리뷰할 시간 있습니다.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room10.getRoomId(), user4.getUserId(), user4.getUserName(), "오늘 오후에 코드 리뷰 할게요. 버그 수정 사항 확인해볼게요.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room10.getRoomId(), user3.getUserId(), user3.getUserName(), "버그 수정은 꽤 시간이 걸렸죠? 대체로 어떻게 해결했나요?",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room10.getRoomId(), user11.getUserId(), user11.getUserName(), "수정한 부분은 로그 분석을 통해 해결했습니다. 코드 리뷰 후 반영할게요.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),


            // ChatRoom 11
            createChatMessage(room11.getRoomId(), user2.getUserId(), user2.getUserName(), "스프린트 백로그 리뷰를 이번 주에 진행하는 게 좋을 것 같습니다.",
                    new Date(System.currentTimeMillis() - 50 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room11.getRoomId(), user4.getUserId(), user4.getUserName(), "좋습니다. 백로그 항목 우선순위 정리가 필요하겠네요.",
                            new Date(System.currentTimeMillis() - 45 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room11.getRoomId(), user3.getUserId(), user3.getUserName(), "회의 일정을 캘린더에 등록해둘게요. 금요일 오후 어떨까요?",
                            new Date(System.currentTimeMillis() - 40 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 12
                    createChatMessage(room12.getRoomId(), user1.getUserId(), user1.getUserName(), "데이터베이스 인덱싱 최적화 작업은 어느 정도 완료되었습니다.",
                            new Date(System.currentTimeMillis() - 60 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room12.getRoomId(), user4.getUserId(), user4.getUserName(), "API 요청 속도 테스트 결과가 어떠셨나요?",
                            new Date(System.currentTimeMillis() - 55 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room12.getRoomId(), user3.getUserId(), user3.getUserName(), "성능 개선이 눈에 띄네요. 배포 일정만 잡으면 될 것 같습니다.",
                            new Date(System.currentTimeMillis() - 50 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 13
                    createChatMessage(room13.getRoomId(), user1.getUserId(), user1.getUserName(), "이번 프로젝트 코드 리뷰 진행 시간 가능하신가요?",
                            new Date(System.currentTimeMillis() - 90 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room13.getRoomId(), user3.getUserId(), user3.getUserName(), "다음 주 화요일 오전에 가능할 것 같습니다.",
                            new Date(System.currentTimeMillis() - 85 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 14
                    createChatMessage(room14.getRoomId(), user7.getUserId(), user7.getUserName(), "이번 프로젝트 UI 디자인 초안 공유드립니다. 피드백 부탁드려요.",
                            new Date(System.currentTimeMillis() - 120 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room14.getRoomId(), user2.getUserId(), user2.getUserName(), "확인했습니다. 몇 가지 수정 요청 사항 코멘트로 남겼습니다.",
                            new Date(System.currentTimeMillis() - 115 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 15
                    createChatMessage(room15.getRoomId(), user6.getUserId(), user6.getUserName(), "로그인 인증 모듈 통합 작업은 어떻게 되고 있나요?",
                            new Date(System.currentTimeMillis() - 180 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room15.getRoomId(), user4.getUserId(), user4.getUserName(), "OAuth 인증 추가로 인해 예상보다 시간이 더 걸리고 있습니다.",
                            new Date(System.currentTimeMillis() - 175 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 16
                    createChatMessage(room16.getRoomId(), user1.getUserId(), user1.getUserName(), "API 문서 자동화 툴 적용해보셨나요?",
                            new Date(System.currentTimeMillis() - 240 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room16.getRoomId(), user2.getUserId(), user2.getUserName(), "Swagger 기반으로 적용을 완료했습니다.",
                            new Date(System.currentTimeMillis() - 235 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room16.getRoomId(), user3.getUserId(), user3.getUserName(), "전체 팀원들에게 사용 방법을 안내해주시면 좋겠습니다.",
                            new Date(System.currentTimeMillis() - 230 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 17
                    createChatMessage(room17.getRoomId(), user6.getUserId(), user6.getUserName(), "CI/CD 파이프라인 문제 해결했습니다.",
                            new Date(System.currentTimeMillis() - 300 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room17.getRoomId(), user4.getUserId(), user4.getUserName(), "수정된 설정 파일 공유 부탁드립니다.",
                            new Date(System.currentTimeMillis() - 295 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 18
                    createChatMessage(room18.getRoomId(), user11.getUserId(), user11.getUserName(), "배포 프로세스 점검이 필요합니다.",
                            new Date(System.currentTimeMillis() - 400 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room18.getRoomId(), user3.getUserId(), user3.getUserName(), "네, 배포 일정과 계획 문서를 작성 중입니다.",
                            new Date(System.currentTimeMillis() - 395 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 19
                    createChatMessage(room19.getRoomId(), user1.getUserId(), user1.getUserName(), "이번 테스트 시나리오 작성 중에 막히는 부분이 있습니다.",
                            new Date(System.currentTimeMillis() - 500 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room19.getRoomId(), user4.getUserId(), user4.getUserName(), "어떤 부분인지 알려주시면 도와드리겠습니다.",
                            new Date(System.currentTimeMillis() - 495 * 60 * 1000), ChatType.TALK),

                    // ChatRoom 20
                    createChatMessage(room20.getRoomId(), user6.getUserId(), user6.getUserName(), "서버 부하 테스트는 언제 진행할 계획인가요?",
                            new Date(System.currentTimeMillis() - 600 * 60 * 1000), ChatType.TALK),
                    createChatMessage(room20.getRoomId(), user11.getUserId(), user11.getUserName(), "다음 주 월요일 오후로 일정을 잡아두었습니다.",
                            new Date(System.currentTimeMillis() - 595 * 60 * 1000), ChatType.TALK)
);

            chatMessageRepository.saveAll(messages);
        };
    }

    private ChatRoom createChatRoom(String name, List<Long> memberIds) {
        ChatRoom room = new ChatRoom();
        room.setRoomId(UUID.randomUUID().toString());
        room.setChatRoomName(name);
        room.setMemberIds(memberIds);
        return room;
    }

    private ChatMessage createChatMessage(String roomId, Long senderId, String senderName, String content, Date sentTime, ChatType chatType) {
        ChatMessage message = new ChatMessage();
        message.setMessageId(ObjectId.get());
        message.setRoomId(roomId);
        message.setSenderId(senderId);
        message.setSenderName(senderName);
        message.setContent(content);
        message.setChatType(chatType);
        message.setSentTime(sentTime);
        return message;
    }
}

