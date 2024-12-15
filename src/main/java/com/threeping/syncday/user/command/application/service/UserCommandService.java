package com.threeping.syncday.user.command.application.service;

import com.threeping.syncday.user.command.application.dto.UserDTO;

public interface UserCommandService {
    void registUser(UserDTO newUser);

    void updatePassword(Long userId, String currentPwd, String newPwd);

    void updateLastAccessTime(String email);

    String getUserNameById(Long senderId);
}
