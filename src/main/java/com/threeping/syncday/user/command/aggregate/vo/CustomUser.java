package com.threeping.syncday.user.command.aggregate.vo;


import com.threeping.syncday.user.command.aggregate.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class CustomUser extends User {
    private Long userId;
    private String userName;
    private String userEmail;

    public CustomUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {

        super(userEntity.getUsername(), userEntity.getPassword(), authorities);
        this.userId = userEntity.getUserId();
        this.userName = userEntity.getUsername();
        this.userEmail = userEntity.getEmail();
    }
}
