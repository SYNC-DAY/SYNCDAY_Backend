package com.threeping.syncday.user.query.service;

import com.threeping.syncday.user.command.domain.aggregate.UserEntity;
import com.threeping.syncday.user.command.domain.repository.UserRepository;
import com.threeping.syncday.user.infrastructure.elasticsearch.document.UserSearchDocument;
import com.threeping.syncday.user.infrastructure.elasticsearch.repository.UserSearchRepository;
import com.threeping.syncday.user.query.dto.UserSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserSearchServiceImpl implements UserSearchService {

    private final UserSearchRepository userSearchRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserSearchServiceImpl(UserSearchRepository userSearchRepository, UserRepository userRepository) {
        this.userSearchRepository = userSearchRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserSearchResponse> searchUser(String keyword) {
        log.info("유저 검색으로 들어온 키워드: {}", keyword);

        List<UserSearchResponse> responseList = userSearchRepository
                .searchByKeyword(keyword).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return responseList;
    }

    private UserSearchResponse convertToResponse(UserSearchDocument doc) {
        return UserSearchResponse.builder()
                .userId(doc.getUserId())
                .name(doc.getName())
                .email(doc.getEmail())
                .profileImage(doc.getProfileImage())
                .teamName(doc.getTeamName())
                .position(doc.getPosition())
                .build();
    }

    private UserSearchResponse convertToUserResponse(UserEntity entity) {
        return UserSearchResponse.builder()
                .userId(entity.getUserId())
                .name(entity.getUserName())
                .email(entity.getEmail())
                .build();
    }

    @Override
    public List<UserSearchResponse> getAllUsers() {
        log.info("전체 유저 목록을 불러옵니다.");
        return userRepository.findAll().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }
}
