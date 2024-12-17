package com.threeping.syncday.user.command.aggregate.vo;


import com.threeping.syncday.user.command.aggregate.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
@Builder
@Getter
@Setter
@ToString
public class CustomUser extends User {

    private Long userId;
    private String username;
    private String userEmail;
    private String profileUrl;

    public CustomUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {

        super(userEntity.getUsername(), userEntity.getPassword(), authorities);
        this.userId = userEntity.getUserId();
        this.username = userEntity.getUsername();
        this.userEmail = userEntity.getEmail();
        this.profileUrl = userEntity.getProfileUrl();
    }
}
