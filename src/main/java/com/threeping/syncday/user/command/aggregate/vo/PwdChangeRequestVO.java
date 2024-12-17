package com.threeping.syncday.user.command.aggregate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PwdChangeRequestVO {

    @Schema(description = "현재 비밀번호", required = true, example = "syncday1211!")
    private String currentPwd;

    @NotEmpty
    @Size(min = 8, max = 20, message = "최소 8자 이상 20자 이하로 구성해야합니다.")
    @Pattern(
            regexp = "^(?!.*(.)\\1{2}).*$",
            message = "동일한 문자를 3번 이상 연속해서 사용할 수 없습니다."
    )
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 포함해야 합니다."
    )
    @Schema(description = "새로운 비밀번호", required = true, example = "syncday1211@")
    private String newPwd;

}
