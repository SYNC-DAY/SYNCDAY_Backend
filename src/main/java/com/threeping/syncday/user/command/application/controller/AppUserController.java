package com.threeping.syncday.user.command.application.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.user.command.aggregate.dto.UserDTO;
import com.threeping.syncday.user.command.aggregate.vo.PwdChangeRequestVO;
import com.threeping.syncday.user.command.aggregate.vo.RegistRequestVO;
import com.threeping.syncday.user.command.aggregate.vo.ResponseNormalLoginVO;
import com.threeping.syncday.user.command.application.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class AppUserController {

    private static final Logger log = LoggerFactory.getLogger(AppUserController.class);
    private final AppUserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AppUserController(AppUserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Operation(summary = "회원가입",
            description = "사원 이름, 이메일, 비밀번호, 전화번호, 프로필사진 등을 입력받아 회원가입을 진행합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원가입 성공",
                            content = @Content(schema = @Schema(implementation = ResponseNormalLoginVO.class))
                    )
            }
    )
    @PostMapping("/regist")
    public ResponseDTO<?> registNewUser(@RequestBody RegistRequestVO requestVO) {

        UserDTO newUser = modelMapper.map(requestVO, UserDTO.class);
        userService.registUser(newUser);

        return ResponseDTO.ok(newUser);
    }

    // 비밀번호 수정은 현재 비밀번호, 바꿀 비밀번호, 바꿀 비밀번호 확인 총 3개를 입력함.
    // 바꿀 비밀번호와 바꿀 비밀번호 확인은 프론트에서 equals로 하고, 백으로 넘어올 값은 현재 비밀번호와 바꿀 비밀번호만 넘어옴.
    // 바꿀 비밀번호의 길이(최소 8자 이상 20자 이하), 영문자포함 여부, 숫자포함, 특수문자 포함은 controller에서 @Valid를 이용해 유효성 검사
    @Operation(summary = "비밀번호 변경",
            description = "현재 비밀번호와 새로운 비밀번호를 입력받아 비밀번호 변경을 요청합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "비밀번호 변경 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                {
                                    "success": true,
                                    "data": "비밀번호가 변경되었습니다.",
                                    "error": null
                                }
                                """
                                    )
                            )
                    )
            }
    )
    @PutMapping("/password/{userId}")
    public ResponseDTO<?> updatePassword(@PathVariable Long userId,
                                         @Valid @RequestBody PwdChangeRequestVO request){
        log.info("비밀번호 요청 들어옴");
        userService.updatePassword(userId, request.getCurrentPwd(), request.getNewPwd());

        return ResponseDTO.ok("비밀번호가 변경되었습니다.");
    }

    @Operation(summary = "로그아웃",
            description = "로그아웃을 요청 시 redis에 저장된 refreshToken을 삭제하고, 발급된 accessToken을 블랙리스트에 들록합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그아웃 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                {
                                    "success": true,
                                    "data": "로그아웃 성공",
                                    "error": null
                                }
                                """
                                    )
                            )
                    )
            }
    )
    @PostMapping("/logout")
    public ResponseDTO<?> logout() {

        return ResponseDTO.ok("로그아웃 성공");
    }
}