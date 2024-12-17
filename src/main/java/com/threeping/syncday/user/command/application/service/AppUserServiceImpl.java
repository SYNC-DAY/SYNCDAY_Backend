package com.threeping.syncday.user.command.application.service;

import com.threeping.syncday.user.command.aggregate.entity.UserEntity;
import com.threeping.syncday.user.command.aggregate.vo.CustomUser;
import com.threeping.syncday.user.command.application.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;
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
}
