package com.threeping.syncday.user.query.service;

import com.threeping.syncday.user.query.dto.UserSearchResponse;

import java.util.List;

public interface UserSearchService {
    List<UserSearchResponse> searchUser(String keyword);

    List<UserSearchResponse> getAllUsers();
}
