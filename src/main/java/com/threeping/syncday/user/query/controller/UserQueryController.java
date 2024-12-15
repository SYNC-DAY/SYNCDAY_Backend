package com.threeping.syncday.user.query.controller;

import com.threeping.syncday.common.ResponseDTO;
import com.threeping.syncday.user.command.application.dto.UserDTO;
import com.threeping.syncday.user.command.domain.aggregate.UserEntity;
import com.threeping.syncday.user.command.domain.repository.UserRepository;
import com.threeping.syncday.user.command.domain.vo.ResponseNormalLoginVO;
import com.threeping.syncday.user.query.dto.UserSearchResponse;
import com.threeping.syncday.user.query.service.UserQueryService;
import com.threeping.syncday.user.query.service.UserSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserQueryController {

    private final UserQueryService userService;
    private final UserSearchService userSearchService;
    private final UserRepository userRepository;

    @Autowired
    public UserQueryController(UserQueryService userService,
                               UserSearchService userSearchService, UserRepository userRepository) {
        this.userService = userService;
        this.userSearchService = userSearchService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Health Check",
            description = "서버 health check end point입니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "마이페이지 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "running on localhost:5000"
                            ))
                    )
            }
    )
    @GetMapping("/health")
    public String health(){
        return "running on localhost:5000";
    }

    @Operation(summary = "마이페이지 조회",
            description = "회원이 Authentication Principal 객체를 통해 마이페이지를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "마이페이지 조회 성공",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))
                    )
            }
    )
    @GetMapping("/profile")
    public ResponseDTO<?> findMyProfile(@AuthenticationPrincipal User user){
        String email = user.getUsername();
        UserDTO userDTO = userService.findByEmail(email);
        return ResponseDTO.ok(userDTO);
    }

    @Operation(summary = "액세스 토큰 재발급 요청 End point",
            description = "쿠키에 리프레시 토큰이 있다면 유효성을 검사한 후 액세스 토큰을 재발급합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "액세스 토큰 재발급 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                {
                                    "success": true,
                                    "data": "액세스 토큰이 재발급되었습니다.",
                                    "error": null
                                }
                                """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/refresh")
    public ResponseDTO<?> refresh(HttpServletRequest request){
        // at만 새로 발급받기 위한 end point
        return ResponseDTO.ok("accessToken 재발급 성공");
    }

    @Operation(summary = "회원 검색",
            description = "이름, 팀이름을 키워드로 회원을 검색합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 검색 성공",
                            content = @Content(schema = @Schema(implementation = UserSearchResponse.class))
                    )
            }
    )
    @GetMapping("/search")
    public ResponseDTO<?> searchUser(@RequestParam String keyword){
        return ResponseDTO.ok(userSearchService.searchUser(keyword));
    }

    @Operation(summary = "회원 pk로 조회",
            description = "회원의 pk를 통해 UserDTO를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 조회 성공",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))
                    )
            }
    )
    @GetMapping("/{userId}")
    public ResponseDTO<?> findUserById(@PathVariable Long userId){
        return ResponseDTO.ok(userService.findById(userId));
    }

    @GetMapping("/select")
    public ResponseDTO<?> getAllUsers() {
        return ResponseDTO.ok(userSearchService.getAllUsers());
    }
}
