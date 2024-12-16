package com.threeping.syncday.user.query.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.user.command.application.dto.UserDTO;
import com.threeping.syncday.user.command.domain.aggregate.CustomUser;
import com.threeping.syncday.user.command.domain.aggregate.UserEntity;
import com.threeping.syncday.user.query.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service("userQueryService")
@Slf4j
class UserQueryServiceImpl implements UserQueryService {

    private final UserMapper userMapper;

    @Autowired
    public UserQueryServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity existingUser = userMapper.findByEmail(username);

        if (existingUser == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // 우선 임의로 회원 권한 모두 USER로 설정. 피드백 받은 후 따로 권한 컬럼을 넣어서 세분화할 예정
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUser(existingUser, grantedAuthorities);
    }

    @Override
    public UserDTO findByEmail(String email) {
        UserEntity user = userMapper.findByEmail(email);

        if (user == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setProfileUrl(user.getProfileUrl());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPosition(user.getPosition());
        userDTO.setTeamId(user.getTeamId());
        userDTO.setLastActivatedAt(user.getLastActivatedAt().toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME));

        return userDTO;
    }

    @Override
    public UserDTO findById(Long userId) {
        UserEntity user = userMapper.findByUserId(userId);

        if(user == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setProfileUrl(user.getProfileUrl());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPosition(user.getPosition());
        userDTO.setTeamId(user.getTeamId());
        userDTO.setLastActivatedAt(user.getLastActivatedAt().toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME));

        return userDTO;
    }

    private String timeStampToString(Timestamp joinYear) {
        return joinYear.toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}