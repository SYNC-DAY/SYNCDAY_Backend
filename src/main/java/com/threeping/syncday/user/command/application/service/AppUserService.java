package com.threeping.syncday.user.command.application.service;


import com.threeping.syncday.user.command.aggregate.dto.UserDTO;
import com.threeping.syncday.user.command.aggregate.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserService extends UserDetailsService {
    void registUser(UserDTO newUser);

    void updatePassword(Long userId, String currentPwd, String newPwd);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    Boolean updateLastActivatedAt(String email);

    String getUserNameById(Long senderId);

    UserEntity getUserById(Long userId);
}