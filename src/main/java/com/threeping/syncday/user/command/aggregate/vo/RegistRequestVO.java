package com.threeping.syncday.user.command.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RegistRequestVO {

    @JsonProperty("username")
    @NotNull
    @Schema(description = "유저 이름", required = true, example = "장그래")
    private String userName;

    @JsonProperty("password")
    @NotNull
    @Schema(description = "유저 비밀번호", required = true, example = "syncday1211!", minLength = 8, maxLength = 20)
    private String password;

    @JsonProperty("email")
    @NotNull
    @Schema(description = "유저 아이디", required = true, example = "syncday1211@gmail.com")
    private String email;

    @JsonProperty("phonenumber")
    @Schema(description = "유저 회사 전화번호", example = "031-000-0000")
    private String phoneNumber;

    @JsonProperty("profilephoto")
    @Schema(description = "유저 프로필 사진", example = "https://i.namu.wiki/i/fUp1O74ZM4BT7WfSGlx6xS4yCt_CbVyaOCCkrqSUh6-HhMf1nLRYWn4n16ckQO0e9wah643tfLpyRHuf0zxfHw.webp")
    private String profilePhoto;

    @JsonProperty("joinyear")
    @Schema(description = "입사연도", example = "2023-01-20")
    private String joinYear;

    @JsonProperty("position")
    @Schema(description = "직책", example = "대리")
    private String position;

    @JsonProperty("teamid")
    @NotNull
    @Schema(description = "팀번호(FK)", example = "1", type = "Long")
    private Long teamId;
}