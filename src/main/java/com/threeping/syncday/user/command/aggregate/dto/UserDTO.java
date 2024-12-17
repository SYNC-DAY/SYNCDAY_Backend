package com.threeping.syncday.user.command.aggregate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    @Schema(description = "유저 고유 번호(PK)", example = "11", type = "Long")
    private Long userId;

    @Schema(description = "유저 이름", example = "장그래")
    private String userName;

    @Schema(description = "유저 이메일", example = "syncday1211@gmail.com")
    private String email;

    @Schema(description = "유저 비밀번호", example = "null")
    private String password;

    @Schema(description = "유저 회사 내선 번호", example = "031-0000-0000")
    private String phoneNumber;

    @Schema(description = "유저 프로필사진", example = "dachshund.jpg")
    private String profilePhoto;

    @Schema(description = "입사연도", example = "20230120")
    private String joinYear;

    @Schema(description = "직책", example = "대리")
    private String position;

    @Schema(description = "팀번호(FK)", example = "1", type = "Long")
    private Long teamId;

    @Schema(description = "마지막 로그인 시각", example = "2024-12-11 14:45:30")
    private String lastAccessTime;
}