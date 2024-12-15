package com.threeping.syncday.user.command.application.service;

import com.threeping.syncday.common.exception.CommonException;
import com.threeping.syncday.common.exception.ErrorCode;
import com.threeping.syncday.user.command.domain.aggregate.UserEntity;
import com.threeping.syncday.user.command.domain.repository.UserRepository;
import com.threeping.syncday.user.command.application.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service("userCommandService")
@Slf4j
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserCommandServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void registUser(UserDTO newUser) {
        // 이미 존재하는지 확인
        UserEntity existingUser = userRepository.findUserByEmail(newUser.getEmail());

        if (existingUser != null) {
            throw new CommonException(ErrorCode.EXIST_USER_ID);
        }

        Timestamp joinYear = convertStringToTimeStamp(newUser.getJoinYear());
        UserEntity user = new UserEntity();
        user.setUserName(newUser.getUserName());
        user.setEmail(newUser.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        user.setJoinYear(joinYear);
        user.setProfilePhoto(newUser.getProfilePhoto());
        user.setPosition(newUser.getPosition());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setTeamId(newUser.getTeamId());
        user.setLastAccessTime(Timestamp.valueOf(LocalDateTime.now()));
        log.info("새로 등록되는 유저정보 registUser: {}", user);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, String currentPwd, String newPwd) {
        UserEntity existingUser =
                userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        log.info("비밀번호 변경 service method 시작");
        log.info("입력받은 현재 비밀번호: {}", currentPwd);
        log.info("입력받은 새 비밀번호: {}", newPwd);
        if (!bCryptPasswordEncoder.matches(currentPwd, existingUser.getPassword())) {
            log.info("잘못된 현재 비밀번호 입력 시 나오는 예외");
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }

        if(bCryptPasswordEncoder.matches(newPwd, existingUser.getPassword())) {
            log.info("새 비밀번호가 현재 비밀번호랑 같은 경우 예외");
            throw new CommonException(ErrorCode.EXIST_PASSWORD);
        }

        newPwd = bCryptPasswordEncoder.encode(newPwd);
        existingUser.setPassword(newPwd);
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void updateLastAccessTime(String email) {
        UserEntity existingUser = userRepository.findUserByEmail(email);

        if (existingUser == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }

        existingUser.setLastAccessTime(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(existingUser);
    }

    private Timestamp convertStringToTimeStamp(String joinYear) {
        try {
            // 날짜만 파싱
            LocalDate date = LocalDate.parse(joinYear,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 원하는 시간 설정 (예: 오전 9시)
            LocalDateTime dateTime = date.atTime(9, 0, 0);

            log.info("변환된 입사연도 LocalDateTime: {}", dateTime);
            return Timestamp.valueOf(dateTime);
        } catch (DateTimeParseException e) {
            log.error("날짜 변환 중 오류 발생: {}", joinYear, e);
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }
    }

    @Override
    public String getUserNameById(Long senderId) {
        String userName = userRepository.findUserNameById(senderId);

        if (userName == null) {
            log.error("해당 유저를 찾을 수 없습니다.: {}", senderId);
        }
        log.info("조회된 유저: {}", userName);
        return userName;
    }
}
