package com.threeping.syncday.user.command.aggregate.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ResponseNormalLoginVO {

    @JsonProperty("userId")
    @Schema(description = "유저 고유번호(PK)", example = "1")
    private Long userId;

    @JsonProperty("userName")
    @Schema(description = "유저 이름", example = "장그래")
    private String userName;

    @JsonProperty("email")
    @Schema(description = "유저 이메일", example = "syncday1211@gmail.com")
    private String email;

    @JsonProperty("profilePhoto")
    @Schema(description = "유저 프로필 사진", example = "dachshund.jpg")
    private String profilePhoto;

    @JsonProperty("joinYear")
    @Schema(description = "입사연도", example = "2023-01-20")
    private String joinYear;

    @JsonProperty("position")
    @Schema(description = "직책", example = "대리")
    private String position;

    @JsonProperty("teamId")
    @Schema(description = "팀번호", example = "1")
    private Long teamId;

    @JsonProperty("lastAccessTime")
    @Schema(description = "마지막 로그인 시각", example = "2024-12-11 15:45:30")
    private String lastAccessTime;
}