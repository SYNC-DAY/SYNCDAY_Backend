package com.threeping.syncday.user.command.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LoginRequestVO {

    @JsonProperty("email")
    @Schema(description = "유저 아이디", required = true, example = "syncday1211@gmail.com")
    private String email;

    @JsonProperty("password")
    @Schema(description = "유저 비밀번호", required = true, example = "syncday1211!", minLength = 8, maxLength = 20)
    private String password;
}