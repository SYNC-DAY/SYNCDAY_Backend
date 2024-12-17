package com.threeping.syncday.user.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.user.command.aggregate.dto.UserDTO;
import com.threeping.syncday.user.command.aggregate.entity.UserEntity;
import com.threeping.syncday.user.command.aggregate.vo.CustomUser;
import com.threeping.syncday.user.command.application.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new UsernameNotFoundException(email);
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new CustomUser(existingUser, grantedAuthorities);
    }


    @Override
    public Boolean updateLastActivatedAt(String email) {
        UserEntity existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new UsernameNotFoundException(email);
        }
        existingUser.setLastActivatedAt(LocalDateTime.now());
        userRepository.save(existingUser);
        return Boolean.TRUE;
    }

    @Override
    public UserEntity getUserById(Long userId) {
        UserEntity foundUser = userRepository.findById(userId).orElse(null);
        if (foundUser == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
        return foundUser;
    }

    @Override
    public String getUserNameById(Long senderId) {
        return "";
    }

    @Override
    public void updatePassword(Long userId, String currentPwd, String newPwd) {

    }

    @Transactional
    @Override
    public void registUser(UserDTO newUser) {
        UserEntity existingUser = userRepository.findUserByEmail(newUser.getEmail());

        if (existingUser != null) {
            throw new CommonException(ErrorCode.EXIST_USER_ID);
        }

        UserEntity user = new UserEntity();
        user.setUserName(newUser.getUserName());
        user.setEmail(newUser.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        user.setPosition(newUser.getPosition());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setTeamId(newUser.getTeamId());
        user.setLastActivatedAt(LocalDateTime.now());
        log.info("새로 등록되는 유저정보 registUser: {}", user);

        userRepository.save(user);
    }
}
